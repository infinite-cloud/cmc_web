$(document).ready(function() {
    let counter;

    window.addEventListener("load", function() {
        let c = sessionStorage.getItem("counter");
        counter = (c) ? c : 1;

        let tbg = sessionStorage.getItem("textBoxesGroup");

        if (tbg) {
            document.getElementById("textBoxesGroup").innerHTML = tbg;
        }

        for (let i = 0; i <= counter; ++i) {
            let value = sessionStorage.getItem("textBox" + i);

            if (value) {
                document.getElementById("textBox" + i).value = value;
            }
        }
    });

    window.addEventListener("beforeunload", function(event) {
        for (let i = 0; i <= counter; ++i) {
            sessionStorage.setItem("textBox" + i,
                document.getElementById("textBox" + i).value);
        }

        return null;
    });

    $("#addButton").click(function() {
        let newTextBox = "<div id = textBoxDiv" + counter +
            "><label><input type = 'text' name = 'authors[" + counter +
            "]' id = 'textBox" + counter + "'/></label></div>";

        $(newTextBox).appendTo("#textBoxesGroup");
        ++counter;
        sessionStorage.setItem("textBoxesGroup",
            document.getElementById("textBoxesGroup").innerHTML);
        sessionStorage.setItem("counter", counter);
    });

    $("#removeButton").click(function() {
        if(counter === 1) {
            return false;
        }

        --counter;
        $("#textBoxDiv" + counter).remove();
        sessionStorage.setItem("counter", counter);
        sessionStorage.setItem("textBoxesGroup",
            document.getElementById("textBoxesGroup").innerHTML);
        sessionStorage.setItem("textBoxDiv" + counter, "");
    });
});

