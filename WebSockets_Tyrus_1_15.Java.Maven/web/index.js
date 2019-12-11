"use strict";

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

  function Affiche(Articles) {
    let htlmArticles = "";
    for (const article of Articles) {
      console.log(article.pics.map((url, index) => `${url}`));
      htlmArticles += `
          <div class="articleContainerRetracted">
            <div class="alwaysVisible">
              <div class="imagesArticle">
                ${article.pics
                  .map(
                    (url, index) =>
                      `<img class="imageArticle" src="${url}" alt ="image${index}" />`
                  )
                  .join("\n                ")}
              </div>
              <div class= "nomArticle" > ${article.name} </div>
              <div class= "prixArticle" > ${article.price.toFixed(2)}€ </div>
              <div class= "condArticles" > ${article.pack} </div>
              <div class= "prixKgArticle" > ${article.pricePerKg.toFixed(2)}€ </div>
              <div class= "descArticle" > ${article.desc} </div>
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
            
            <div class= "hidden">
              <div class= "ingreArticles" > ${article.ingr} </div>
              <div class= "allergenesArticles" > ${article.allerg} </div>
              <div class= "addArticle" > ${article.addit} </div>
              <div class= "presArticle" > ${article.pres} </div>
              <div class= "tableauxInfoNutritionel" >
                <table>
                ${article.tabNut
                  .map(
                    (col, index) =>
                      `<td> 
                      ${col
                        .map((row, index) => `<tr>${row}</tr>`)
                        .join("\n                      ")}
                    <td>`
                  )
                  .join("\n                ")}}
                  </table>
              </div>
            </div>
          </div>
                        
      
      `;
    }
    let app = document.getElementById("AppContainer");
    app.innerHTML = htlmArticles;
  }

  // Fonctions de L'interface---------------------------------------------------------------

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
