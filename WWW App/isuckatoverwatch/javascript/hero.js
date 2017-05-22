function getStatsForHero(uname, hero, mode){
  console.log(hero.replace("","[empty]"))
  if(hero == ""){
    alert("ERROR: Please choose a hero first!")
    return
  }
  uname = API.sanitizeUsername(uname)

  API.removeHeroSelector();
  API.showLoader();
  API.getStatsForHero(uname, API.PLATFORM.PC, API.REGION.USA, mode, hero, function(json_data, ok){
    if(ok){
      console.log(json_data)
      var heroData = json_data[hero]
      heroData["heroname"] = hero
      API.post("hero.php", heroData, {"uname": uname, "hero": hero, "mode": mode})
    }
    else{

      API.hideLoader()
    }
  })
}
