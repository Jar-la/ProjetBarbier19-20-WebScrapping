"use strict";

var nbArticlesAffiche = 0;
window.onload = () => {
  // Tested with Tyrus 1.15 WebSockets Java library
  let service = new WebSocket(
    "ws://localhost:1963/FranckBarbier/WebSockets_illustration"
  );
  service.onmessage = event => {
    //console.log("Message from Java: " + event.data);
    let productArray = JSON.parse(event.data);
    Affiche(productArray);
  };

  service.onopen = () => {
    console.log("service.onopen...");
    /*
      let response = window.confirm(service.url + " just opened... Say 'Hi!'?");
      if (response)
          service.send(JSON.stringify({Response: "Hi!"}));*/
  };
  service.onclose = (event /*:CloseEvent*/) => {
    console.log("service.onclose... " + event.code);
    window.alert("Bye! See you later...");
    // '1011': the server is terminating the connection because it encountered an unexpected condition that prevented it from fulfilling the request.
  };
  service.onerror = () => {
    window.alert("service.onerror...");
  };

  //Fonctions de modification de l'affichage--------------------------------------------------

  function getCells(data, type) {
    return data.map(cell => `<${type}>${cell}</${type}>`).join("");
  }

  function createBody(data) {
    return data.map(row => `<tr>${getCells(row, "td")}</tr>`).join("");
  }

  function createTable(data) {
    const [headings, ...rows] = data;
    return `
      <table>
        <thead>${getCells(headings, "th")}</thead>
        <tbody>${createBody(rows)}</tbody>
      </table>
    `;
  }

  function Affiche(Articles) {
    let htlmArticles = "";
    let i = 0;
    for (const article of Articles) {
      htlmArticles += `
          <div class="articleContainerRetracted" id="${i}" >
            <div class="alwaysVisible">
              <div class="imagesArticle">
                ${article.pics
                  .map(
                    (url, index) =>
                      `<img class="imageArticle" src="${url}" alt ="image${index}"  />`
                  )
                  .join("\n                ")}
              </div>
              <div class= "nomArticle" > Nom:${article.name} </div>
              <div class= "prixArticle" > Prix:${article.price.toFixed(2)}€ </div>
              <div class= "condArticles" > Quantité:${article.pack} </div>
              <div class= "prixKgArticle" > Prix/kg:${article.pricePerKg.toFixed(
                2
              )}€ </div>
              <div class= "descArticle" > Description: <br/>${article.desc} </div>
              <div class="signsArticles">
                ${article.sign
                  .map(
                    (url, index) =>
                      `<img class="signArticles" src="${url}" alt ="sign${index}" />`
                  )
                  .join("\n                ")}
              </div>
              <div class="nutriScoreArticle">
                <img class="imgNutriScoreArticle" src="${
                  article.nutScore
                }" alt="nutriScore">
              </div>      
            </div>
            
            <div class= "hidden" id="h${i}">
              <div class= "ingreArticles" >Ingredients: <br/> ${article.ingr} </div>
              <div class= "allergenesArticles" >Allergènes: <br/> ${article.allerg} </div>
              <div class= "addArticle" >Additifs: </br> ${article.addit} </div>
              <div class= "presArticle" >Conservation: </br> ${article.pres} </div>
              <div class= "tableauxInfoNutritionel" >
               ${createTable(article.tabNut)}
              </div>
            </div>
          </div>
                        
      `;
      i -= -1;
    }
    document.getElementById("AppContainer").innerHTML = htlmArticles;
    nbArticlesAffiche = --i;

    for (let j = i; j >= 0; j--) {
      document.getElementById(j).addEventListener("click", etendre);
    }
  }

  // Fonctions de L'interface---------------------------------------------------------------

  //Change la façons d'afficher l'article cliqué en changeant sa classe
  function etendre() {
    //console.log(`h${this.id}`);
    this.classList.remove("articleContainerRetracted");
    this.classList.add("articleContainerExtended");
    document.getElementById(`h${this.id}`).classList.remove("hidden");
    document.getElementById(`h${this.id}`).classList.add("visibleWhenExtended");
    this.removeEventListener("click", etendre);
    this.addEventListener("click", retracter);
  }
  function retracter() {
    this.classList.remove("articleContainerExtended");
    this.classList.add("articleContainerRetracted");
    document.getElementById(`h${this.id}`).classList.remove("visibleWhenExtended");
    document.getElementById(`h${this.id}`).classList.add("hidden");
    this.removeEventListener("click", retracter);
    this.addEventListener("click", etendre);
  }

  // Changer l'affichage du nombre d'article en fonction du slidder.
  let sliderArticles = document.getElementById("rangeNbArticle");
  let outputArticles = document.getElementById("nbArticles");
  outputArticles.innerHTML = sliderArticles.value;
  sliderArticles.oninput = function() {
    outputArticles.innerHTML = this.value;
  };

  // Gestion de la recherche
  let buttonR = document.getElementById("buttonR");

  buttonR.onclick = function request() {
    let quantity = sliderArticles.value;
    let search = document.getElementById("s").value;
    // On enleve tous ce qui n'est pas un chiffre ou une lettre
    let searchReg = search.replace(/[^a-z0-9]/gi, "");
    // Envoie la saisie de l'utilisateur fitré et la quantité
    let data = service.send(
      JSON.stringify({ Request: `${searchReg}`, Quantity: `${quantity}` })
    );
  };
};
