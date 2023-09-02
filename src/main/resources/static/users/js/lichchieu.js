const $ = document.querySelector.bind(document)
const $$ = document.querySelectorAll.bind(document)

const tabActive = $('.tab-item.active');
const line = $('.tabs .line')

line.style.left = tabActive.offsetLeft  +'px';
line.style.width = tabActive.offsetWidth  +'px';


const tabs = $$('.tab-item')
const panes = $$('.tab-pane')

tabs.forEach((tab,index) => {
  const pane = panes[index];

  tab.onclick = function (){
    $('.tab-item.active').classList.remove('active');
    $('.tab-pane.active').classList.remove('active');

    line.style.left = this.offsetLeft + 0 +'px';
    line.style.width = this.offsetWidth  +'px';

    this.classList.add('active');
    pane.classList.add('active');
  }
});

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