"use strict";

let oldRequest = "";
let oldQuantity = null;

window.onload = () => {
  // Tested with Tyrus 1.15 WebSockets Java library
  let service = new WebSocket("ws://localhost:1963/LafonVanootegem/WebSockets");

  service.onmessage = event => {
    let productArray = JSON.parse(event.data);
    Affiche(productArray);
  };

  service.onopen = () => {
    console.log("service.onopen...");
  };

  service.onclose = event  => {
    console.log("service.onclose... " + event.code);
    window.alert("Bye! See you later...");
    window.close();
    // '1011': the server is terminating the connection because it encountered an unexpected condition that prevented it from fulfilling the request.
  };

  service.onerror = () => {
    window.alert("service.onerror...");
  };

  // Changer l'affichage du nombre d'article en fonction du slidder.
  let sliderArticles = document.getElementById("rangeNbArticle");
  let outputArticles = document.getElementById("nbArticles");
  outputArticles.innerHTML = sliderArticles.value;
  sliderArticles.oninput = function() {
    outputArticles.innerHTML = this.value;
  };

  // Gestion de la recherche-----------------------------------------------------------------------

  let buttonR = document.getElementById("buttonR");
  buttonR.onclick = function request() {
    let quantity = document.getElementById("rangeNbArticle").value;
    let search = document.getElementById("s").value;

    // On enleve tous ce qui n'est pas un chiffre,une lettre ou un espace
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
