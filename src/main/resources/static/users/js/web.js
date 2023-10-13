//
//
// // Obtener referencias a los elementos del DOM
// // Obtener referencias a los elementos del DOM
// var modal = document.getElementById("myModal");
// var btn = document.getElementById("myBtn");
// var closeBtn = document.getElementsByClassName("close")[0];
//
// // Abrir modal al hacer clic en el botón1
// //đang chiếu
// // btn.addEventListener("click", function() {
// //     modal.style.display = "block";
// // });
//
// // Cerrar modal al hacer clic en el botón de cerrar
// closeBtn.addEventListener("click", function() {
//     modal.style.display = "none";
// });
//
// // Cerrar modal al hacer clic fuera del contenido del modal
// window.addEventListener("click", function(event) {
//     if (event.target == modal) {
//         modal.style.display = "none";
//     }
// });
// //sắp chiếu
// var modall = document.getElementById("myModall");
// var btnn = document.getElementById("myBtnn");
// var closeBtnn = document.getElementsByClassName("closee")[0];
//
// // Abrir modal al hacer clic en el botón
// // btnn.addEventListener("click", function() {
// //     modall.style.display = "block";
// // });
//
// // Cerrar modal al hacer clic en el botón de cerrar
// closeBtnn.addEventListener("click", function() {
//     modall.style.display = "none";
// });
//
// // Cerrar modal al hacer clic fuera del contenido del modal
// window.addEventListener("click", function(event) {
//     if (event.target == modall) {
//         modall.style.display = "none";
//     }
// });





// dat ve
// var modal1 = document.getElementById("myModal1");
// var btn1 = document.getElementById("myBtn1");
// var closeBtn1 = document.getElementsByClassName("close1")[0];
//
// // Abrir modal al hacer clic en el botón
// btn1.addEventListener("click", function() {
//     modal1.style.display = "block";
// });
//
// // Cerrar modal al hacer clic en el botón de cerrar
// closeBtn1.addEventListener("click", function() {
//     modal1.style.display = "none";
// });
//
// // Cerrar modal al hacer clic fuera del contenido del modal
// window.addEventListener("click", function(event) {
//     if (event.target == modal1) {
//         modal1.style.display = "none";
//     }
// });
// // lịch chiếu
//

// const $ = document.querySelector.bind(document)
// const $$ = document.querySelectorAll.bind(document)
//
// const tabActive = $('.tab-item.active');
// const line = $('.tabs .line')
//
// line.style.left = tabActive.offsetLeft  +'px';
// line.style.width = tabActive.offsetWidth  +'px';
//
//
// const tabs = $$('.tab-item')
// const panes = $$('.tab-pane')
//
// tabs.forEach((tab,index) => {
//     const pane = panes[index];
//
//     tab.onclick = function (){
//         $('.tab-item.active').classList.remove('active');
//         $('.tab-pane.active').classList.remove('active');
//
//         line.style.left = this.offsetLeft - 5 +'px';
//         line.style.width = this.offsetWidth  +'px';
//
//         this.classList.add('active');
//         pane.classList.add('active');
//     }
// });


//




const modalTriggerButtons = document.querySelectorAll("[data-modal-target]");
const modals = document.querySelectorAll(".modal-view");
const modalCloseButtons = document.querySelectorAll(".modal-close-view");

modalTriggerButtons.forEach(elem => {
    elem.addEventListener("click", event => toggleModal(event.currentTarget.getAttribute("data-modal-target")));
});
modalCloseButtons.forEach(elem => {
    elem.addEventListener("click", event => toggleModal(event.currentTarget.closest(".modal-view").id));
});
modals.forEach(elem => {
    elem.addEventListener("click", event => {
        if(event.currentTarget === event.target) toggleModal(event.currentTarget.id);
    });
});

// Maybe also close with "Esc"...
document.addEventListener("keydown", event => {
    if(event.keyCode === 27 && document.querySelector(".modal-view.modal-show")) {
        toggleModal(document.querySelector(".modal-view.modal-show").id);
    }
});

function toggleModal(modalId) {
    const modal = document.getElementById(modalId);

    if(getComputedStyle(modal).display==="flex") { // alternatively: if(modal.classList.contains("modal-show"))
        modal.classList.add("modal-hide");
        setTimeout(() => {
            document.body.style.overflow = "initial"; // Optional: in order to enable/disable page scrolling while modal is hidden/shown - in this case: "initial" <=> "visible"
            modal.classList.remove("modal-show", "modal-hide");
            modal.style.display = "none";
        }, 200);
    }
    else {
        document.body.style.overflow = "hidden"; // Optional: in order to enable/disable page scrolling while modal is hidden/shown
        modal.style.display = "flex";
        modal.classList.add("modal-show");
    }
}
//
// menu
const menuSlide = () => {
    const menuIcon = document.querySelector(".menu-icon");
    const navLinks = document.querySelector(".nav-links");
    const navLinksInner = document.querySelectorAll(".nav-links li");

    //menu-icon click event
    menuIcon.addEventListener("click", () => {
        //toggle active class
        navLinks.classList.toggle("menu-active");

        //animate navLinks
        navLinksInner.forEach((li, index) => {
            if (li.style.animation) {
                li.style.animation = "";
            } else {
                li.style.animation = `navLinkAnime 0.5s ease forwards ${
                    index / 7 + 0.3
                }s`;
            }
        });

        //toggle for menu-icon animation
        menuIcon.classList.toggle("span-anime");
    });
};

menuSlide();

//
var slideIndex = 1;
showSlides(slideIndex);

function plusSlide(n) {
    showSlides((slideIndex += n));
}

function currentSlide(n) {
    showSlides((slideIndex = n));
}

function showSlides (n){
    var i;
    var slides = document.getElementsByClassName("slide");
    var dots = document.getElementsByClassName("dot");

    if (n > slides.length) {
        slideIndex = 1
    }
    if (n < 1) {
        slideIndex = slides.length
    }
    for (i = 0; i < slides.length; i++){
        slides[i].style.display = "none";
    }
    for (i = 0; i < dots.length; i++){
        dots[i].className = dots[i].className.replace("active","");
    }
    slides[slideIndex-1].style.display = "block";
    dots[slideIndex-1].className+= " active";
}



function slideTime(n){
    n=1
    showSlides(slideIndex += n);
}

setInterval(slideTime, 4000);