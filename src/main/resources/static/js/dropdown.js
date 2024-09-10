"use strict"

document.querySelectorAll(".dropdown").forEach((dropDownWrapper) => {
    const dropDownBtn = dropDownWrapper.querySelector(".dropdown-button");
    const dropDownList = dropDownWrapper.querySelector(".dropdown-list");
    const dropDownListItems = dropDownList.querySelectorAll(".dropdown-list-item");
    const dropDownInput = dropDownWrapper.querySelector(".dropdown-input");

    dropDownBtn.addEventListener("click", (btn) => {
        dropDownList.classList.toggle("hide");
        dropDownListItems.forEach((listItem) => {
            listItem.classList.remove("choose");
            if (dropDownBtn.textContent === listItem.textContent) {
                listItem.classList.add("choose");
                listItem.scrollIntoView();
            }
        })
    });

    dropDownList.addEventListener("mouseover", (evt) => {
        let item = evt.target.closest(".dropdown-list-item");
        if (!item) return;
        dropDownListItems.forEach((listItem) => {
            listItem.classList.remove("choose");
        })
        item.classList.add("choose");
    })

    dropDownListItems.forEach((listItem) => {
        listItem.addEventListener("click", (evt) => {
            evt.stopPropagation();
            dropDownBtn.textContent = listItem.textContent;
            dropDownInput.value = listItem.dataset.value;
            dropDownList.classList.add("hide");
        })
    });

    document.addEventListener("click", (evt) => {
        if (evt.target !== dropDownBtn) {
            dropDownList.classList.add("hide");
        }
    });

    document.addEventListener("keydown", (evt) => {
        if (evt.key === "Escape" || evt.key === "Tab") {
            dropDownBtn.blur();
            dropDownList.classList.add("hide");
        }
    });
})