"use strict"

const dropDownBtn = document.querySelector(".dropdown-button");
const dropDownList = document.querySelector(".dropdown-list");
const dropDownListItems = dropDownList.querySelectorAll(".dropdown-list-item");

dropDownBtn.addEventListener("click", () => {
    dropDownList.classList.toggle("hide");
    if (!dropDownList.classList.contains("hide")) {
        dropDownListItems.forEach(i => {
            if (i.textContent === dropDownBtn.textContent) {
                i.classList.add("choose");
            }
        })
    }
})

document.addEventListener("click", (evt) => {
    if (evt.target !== dropDownBtn) {
        dropDownList.classList.add("hide");
    }
})

document.addEventListener("keydown", (evt) => {
    if ( evt.key === "Escape") {
        dropDownList.classList.add("hide");
    }
})