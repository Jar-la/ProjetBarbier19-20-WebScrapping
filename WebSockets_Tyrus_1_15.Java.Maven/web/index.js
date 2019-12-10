"use strict";

window.onload = () => {
  // Changer l'affichage du nombre d'article en fonction du slidder.
  let sliderArticles = document.getElementById("rangeNbArticle");
  let outputArticles = document.getElementById("nbArticles");
  outputArticles.innerHTML = sliderArticles.value;

  sliderArticles.oninput = function() {
    outputArticles.innerHTML = this.value;
  };
};

// Tested with Tyrus 1.15 WebSockets Java library
let service = new WebSocket("ws://localhost:1963/FranckBarbier/WebSockets_illustration");

service.onmessage = event => {
  console.log("Message from Java: " + event.data);

  let productArray = JSON.parse(event.data);

  for (let i = 0; i < productArray.length; i++) {
    let produit = productArray[i];
    console.log(produit);
  }
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

// Création de la requette pour les articles
function request() {
  console.log(document.getElementById("s").value);
  let search = document.getElementById("s").value;
  let searchReg = search.replace(/[^a-z0-9]/gi, "");
  let quantity = 1;
  service.send(JSON.stringify({ Request: `${searchReg}`, Quantity: `${quantity}` }));
}
