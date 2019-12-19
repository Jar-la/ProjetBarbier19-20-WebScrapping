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
      // Balise pour le nom de l'article
      let nom = ``;
      if (article.name != null) {
        nom = `<div class= "nomArticle" > Nom:${article.name} </div>`;
      }
      // Balise pour le prix
      let prix = ``;
      if (article.price != null) {
        prix = `<div class= "prixArticle" > Prix:${article.price} </div>`;
      }
      // Balise pour le conditionement
      let cond = ``;
      if (article.pack != null) {
        cond = `<div class= "condArticles" > Quantité:${article.pack} </div>`;
      }
      // Balise pour le prix au Kg ou L
      let prixKg = ``;
      if (article.pricePerKg != null) {
        prixKg = `<div class= "prixKgArticle" > Prix/kg:${article.pricePerKg} </div>`;
      }
      // Balsie de la description du produit
      let desc = ``;
      if (article.desc != null) {
        desc = `<div class= "descArticle" > Description: <br/>${article.desc} </div>`;
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
        ingr = `<div class= "ingreArticles" >Ingredients: <br/> ${article.ingr} </div>`;
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
      if (article.tabNut != null) {/*
        tabNut = `<div class= "tableauxInfoNutritionel" >
                    ${createTable(article.tabNut)}
                  </div>`;*/
      }

      // On ajoute les balises aux different containers
      htlmArticles += `
          <div class="articleContainerRetracted" id="${i}" >
            <div class="alwaysVisible">
              ${image}
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
    console.log(i);
    for (let j = i; j > 0; j--) {
      document.getElementById(j).addEventListener("click", etendre);
      console.log(j);
    }
  }