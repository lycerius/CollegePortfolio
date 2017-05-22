function getHeroInformationByName(username, mode){
  API.showLoader();
  username = API.sanitizeUsername(username)
  console.log("searching for username="+username)
  API.getPlayerCard(username, API.PLATFORM.PC, API.REGION.USA, function(playerCard, ok){
    if(ok){
      API.getDetailedStats(username, API.PLATFORM.PC, API.REGION.USA, mode, function (json_data, ok){
        if(!ok){
          API.hideLoader()
        }
        else {
          json_data["playerCard"] = JSON.stringify(playerCard)
          API.post("byname.php",json_data, {"uname": username, "mode" : mode})
        }
      })
    }
    else{
      API.hideLoader()
    }

  })


}
