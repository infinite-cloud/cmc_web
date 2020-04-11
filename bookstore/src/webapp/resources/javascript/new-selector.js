$(document).ready(function() {
    let counter;

    window.addEventListener("load", function() {
        let c = sessionStorage.getItem("selectorCounter");
        counter = (c) ? c : 1;

        let sg = sessionStorage.getItem("selectorGroup");

        if (sg) {
            document.getElementById("selectorGroup").innerHTML = sg;
        }

        for (let i = 0; i <= counter; ++i) {
            let value = sessionStorage.getItem("selector" + i);

            if (value) {
                document.getElementById("selector" + i).value = value;
            }
        }
    });

    window.addEventListener("beforeunload", function(event) {
        for (let i = 0; i < counter; ++i) {
            sessionStorage.setItem("selector" + i,
                document.getElementById("selector" + i).value);
        }

        return null;
    });

    $("#addButton").click(function() {
        let newSelector = "<div id = 'selectorDiv" + counter +
            "'><label><select name = 'bookAuthors[" + counter +
            "]' id = 'selector" + counter + "'>" +
            document.getElementById("selector0").innerHTML +
            "</select></label></div>";

        $(newSelector).appendTo("#selectorGroup");
        ++counter;
        sessionStorage.setItem("selectorGroup",
            document.getElementById("selectorGroup").innerHTML);
        sessionStorage.setItem("selectorCounter", counter);
    });

    $("#removeButton").click(function() {
        if(counter === 1) {
            return false;
        }

        --counter;
        $("#selectorDiv" + counter).remove();
        sessionStorage.setItem("selectorCounter", counter);
        sessionStorage.setItem("selectorGroup",
            document.getElementById("selectorGroup").innerHTML);
        sessionStorage.setItem("selectorDiv" + counter, "");
    });
});

