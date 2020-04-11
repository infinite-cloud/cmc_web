const selectors = {
    '#genre' : 'selectedGenre',
    '#cover' : 'selectedCover',
    '#publisher' : 'selectedPublisher'
};

window.addEventListener("load", function() {
    for (const [key, value] of Object.entries(selectors)) {
        let selectedItem = sessionStorage.getItem(value);
        $(key).val(selectedItem);
    }
});

for (const [key, value] of Object.entries(selectors)) {
    $(key).live('change', function() {
        let selectedValue = $(this).val();
        sessionStorage.setItem(value, selectedValue);
    });
}

$('#reset').live('click', function() {
    sessionStorage.clear();
    $('#textBox0').val('');
});
