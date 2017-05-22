function compareStats(username1, username2, mode, hero){
  if(hero == ""){
    alert("ERROR: Please choose a hero first!")
    return
  }
  if(username1 == "" || username2 == ""){
    alert("ERROR: Please enter both usernames")
    return
  }
  API.showLoader()
  username1 = API.sanitizeUsername(username1)
  username2 = API.sanitizeUsername(username2)
  API.getStatsForHero(username1, API.PLATFORM.PC, API.REGION.USA, mode, hero, function(json_data_1, ok){
    if(ok){
      API.getStatsForHero(username2, API.PLATFORM.PC, API.REGION.USA, mode, hero, function(json_data_2 , ok2){
        if(ok2){
          API.removeHeroSelector()
          var combination = {}
          combination[username1] = json_data_1
          combination[username2] = json_data_2
          combination["keys"] = [username1, username2]
          console.log(combination)
          console.log(JSON.stringify(combination))
          API.post("comparehero.php", {"combo": JSON.stringify(combination)}, {"uname1": username1, "uname2": username2, "mode": mode, "hero" : hero})
        }
        else {
          API.hideLoader()
        }

      })
    }
    else{
      API.hideLoader()
    }

  })


}
