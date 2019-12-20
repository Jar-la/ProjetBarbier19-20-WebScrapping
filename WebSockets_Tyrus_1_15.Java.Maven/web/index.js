"use strict";

window.onload = () => {
  // Tested with Tyrus 1.15 WebSockets Java library
  let service = new WebSocket("ws://localhost:1963/LafonVanootegem/WebSockets");
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
    window.close();
    // '1011': the server is terminating the connection because it encountered an unexpected condition that prevented it from fulfilling the request.
  };
  service.onerror = () => {
    window.alert("service.onerror...");
  };

  // Fonctions de modification de l'affichage--------------------------------------------------

  // Genere le code html des articles
  function Affiche(Articles) {
    let htlmArticles = ""; // Code qui sera affiché dans la page
    let i = 0; // Stocke le nombre d'article pour pouvoir y ajouter des evenements
    for (const article of Articles) {
      // Incrementer i
      i -= -1;
      // Balise pour l'image
      let image = ``;
      if (article.pics != null) {
        image = `<img class="imageArticle" src="${article.pics}"  />`;
      }
      // Balise pour la marque de l'article
      let marque = ``;
      if (article.brand != null) {
        marque = `<div class= "marqueArticle" > <span>Marque</span> : ${article.brand} </div>`;
      }

      // Balise pour le nom de l'article
      let nom = ``;
      if (article.name != null) {
        nom = `<div class= "nomArticle" > <span>Nom</span> : ${article.name} </div>`;
      }
      // Balise pour le prix
      let prix = ``;
      if (article.price != null) {
        prix = `<div class= "prixArticle" > <span>Prix</span> : ${article.price} </div>`;
      }
      // Balise pour le conditionement
      let cond = ``;
      if (article.pack != null) {
        cond = `<div class= "condArticles" ><span>Quantité</span>:<br>${article.pack} </div>`;
      }
      // Balise pour le prix au Kg ou L
      let prixKg = ``;
      if (article.pricePerKg != null) {
        prixKg = `<div class= "prixKgArticle" > <span>Prix/kg</span> : <br>${article.pricePerKg} </div>`;
      }
      // Balsie de la description du produit
      let desc = ``;
      if (article.desc != null) {
        desc = `<div class= "descArticle" > <span>Description</span> : ${article.desc} </div>`;
      }
      // Balise pour les images de signaletique
      let sign = ``;
      if (article.sign != null) {
        sign = `<div class="signsArticles">
                  ${article.sign
                    .map(
                      (url, index) =>
                        `<img class="signArticles" src="${url}" alt ="sign${index}" />`
                    )
                    .join("\n                ")}
                </div>`;
      }
      // Balise de l'image nutriScore
      let nut = ``;
      if (article.nutScore != null) {
        nut = ` <div class="nutriScoreArticle">
                  <img class="imgNutriScoreArticle" src="${article.nutScore}" alt="nutriScore">
                </div>`;
      }
      //  Balise pour les ingredients
      let ingr = ``;
      if (article.ingr != null) {
        ingr = `<div class= "ingreArticles" ><span>Ingredients</span> : <br/> ${article.ingr} </div>`;
      }
      // Balise pour les allergenes
      let allg = ``;
      if (article.allerg != null) {
        allg = `<div class= "allergenesArticles" >Allergènes: <br/> ${article.allerg} </div>`;
      }
      // Balise pour les additifs
      let addi = ``;
      if (article.addit != null) {
        addi = `<div class= "addArticle" >Additifs: </br> ${article.addit} </div>`;
      }
      // Balise pour les conseil de conservation
      let pres = ``;
      if (article.pres != null) {
        pres = `<div class= "presArticle" >Conservation: </br> ${article.pres} </div>`;
      }
      // balise pour le tableau des information nutritionelles
      let tabNut = ``;
      if (article.tabNut != null) {
        /*
        tabNut = `<div class= "tableauxInfoNutritionel" >
                    ${createTable(article.tabNut)}
                  </div>`;*/
      }

      // On ajoute les balises aux different containers
      htlmArticles += `
          <div class="articleContainerRetracted" id="${i}" >
            <div class="alwaysVisible">
              ${image}
              ${marque}
              ${nom}
              ${prix}
              ${cond}
              ${prixKg}
              ${desc}
              ${sign}
              ${nut}
            </div>

            <div class= "hidden" id="h${i}">
              ${ingr}
              ${allg}
              ${addi}
              ${pres}
              ${tabNut}
            </div>
          </div>

      `;
    }
    document.getElementById("AppContainer").innerHTML = htlmArticles;
    for (let j = i; j > 0; j--) {
      document.getElementById(j).addEventListener("click", etendre);
    }
  }

  // Fonctions de creation d'un tableau
  function createTable(data) {
    const [headings, ...rows] = data;
    return `
      <table>
        <thead>${getCells(headings, "th")}</thead>
        <tbody>${createBody(rows)}</tbody>
      </table>
    `;
  }
  function getCells(data, type) {
    return data.map(cell => `<${type}>${cell}</${type}>`).join("");
  }
  function createBody(data) {
    return data.map(row => `<tr>${getCells(row, "td")}</tr>`).join("");
  }

  // Fonctions de L'interface---------------------------------------------------------------

  //Change la façons d'afficher l'article cliqué en changeant sa classe
  function etendre() {
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

  // Gestion de la recherche-----------------------------------------------------------------------

  let buttonR = document.getElementById("buttonR");
  let oldRequest = "";
  let oldQuantity = null;
  buttonR.onclick = function request() {
    let quantity = sliderArticles.value;
    let search = document.getElementById("s").value;

    // On enleve tous ce qui n'est pas un chiffre ou une lettre
    let searchReg = search.replace(/[^a-z0-9éèàêùç\s]/gi, "");

    // Envoie la saisie de l'utilisateur fitré et la quantité si la chaine est non vide et
    // si la recherche ou la quantité à changée
    if (searchReg != "" && (searchReg != oldRequest || quantity != oldQuantity)) {
      oldRequest = searchReg;
      oldQuantity = quantity;
      let data = service.send(
        JSON.stringify({ Request: `${searchReg}`, Quantity: `${quantity}` })
      );
    }
  };
};
