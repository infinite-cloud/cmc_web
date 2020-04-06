function toggle(buttonText) {
    const element = document.getElementById("toggleText");
    const text = document.getElementById("displayText");
    if (element.style.display === "block") {
        element.style.display = "none";
        text.innerHTML = buttonText;
    }
    else {
        element.style.display = "block";
        text.innerHTML = buttonText;
    }
}
