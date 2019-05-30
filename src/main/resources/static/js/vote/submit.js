function submitVote(choice) {
    if (choice == 1) {
        alert($('input[name="optionsRadios"]:checked ').attr('id'));
    }
}