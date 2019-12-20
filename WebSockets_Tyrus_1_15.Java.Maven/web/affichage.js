// Fonctions de modification de l'affichage
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
    //  Balise pour les ingredients
    let ingr = ``;
    if (article.ingr != null) {
      ingr = `<div class= "ingreArticles" ><span>Ingredients</span> : <br/> ${article.ingr} </div>`;
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
            </div>
            <div class= "hidden" id="h${i}">
              ${ingr}
            </div>
          </div>
      `;
  }
  document.getElementById("AppContainer").innerHTML = htlmArticles;

  // Boucle d'ajout de l'évenement qui sert a étendre l'affichage d'un article
  for (let j = i; j > 0; j--) {
    document.getElementById(j).addEventListener("click", etendre);
  }
}

// Change la façons d'afficher l'article cliqué en changeant sa classe
// et change l'EventListener qui lui est lié
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
