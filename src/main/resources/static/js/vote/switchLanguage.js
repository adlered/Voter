function switchLanguage(language, VoteID) {
    if (language == "cn") {
        location.href = "/vote/cn/" + VoteID;
    } else if (language == "en") {
        location.href = "/vote/en/" + VoteID;
    }
}