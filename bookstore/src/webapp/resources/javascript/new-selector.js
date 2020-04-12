$(document).ready(function() {
    let counter;
    let id = window.location.href;

    window.addEventListener("load", function() {
        let selectors = document.getElementsByClassName("selectors");
        let c = sessionStorage.getItem("selectorCounter" + id);
        counter = (c) ? c : selectors.length - 1;

        let sg = sessionStorage.getItem("selectorGroup" + id);

        if (sg) {
            document.getElementById("selectorGroup").innerHTML = sg;
        }

        for (let i = 0; i < counter; ++i) {
            let value = sessionStorage.getItem("selector" + i + id);

            if (value) {
                document.getElementById("selector" + i).value = value;
            }
        }
    });

    window.addEventListener("beforeunload", function(event) {
        for (let i = 0; i < counter; ++i) {
            sessionStorage.setItem("selector" + i + id,
                document.getElementById("selector" + i).value);
        }

        return null;
    });

    $("#addButton").click(function() {
        let newSelector = "<div id = 'selectorDiv" + counter +
            "'><label><select name = 'bookAuthors[" + counter +
            "]' id = 'selector" + counter + "' class = 'selectors'>" +
            document.getElementById("selector").innerHTML +
            "</select></label></div>";

        $(newSelector).appendTo("#selectorGroup");
        ++counter;
        sessionStorage.setItem("selectorGroup" + id,
            document.getElementById("selectorGroup").innerHTML);
        sessionStorage.setItem("selectorCounter" + id, counter);
    });

    $("#removeButton").click(function() {
        if(counter <= 1) {
            return false;
        }

        --counter;
        $("#selectorDiv" + counter).remove();
        sessionStorage.setItem("selectorCounter" + id, counter);
        sessionStorage.setItem("selectorGroup" + id,
            document.getElementById("selectorGroup").innerHTML);
        sessionStorage.setItem("selectorDiv" + counter + id, "");
    });
});
