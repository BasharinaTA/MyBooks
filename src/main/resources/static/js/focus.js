const input = document.querySelector("[autofocus]");
if (input != null) {
    input.setSelectionRange(input.value.length, input.value.length);
}
