var scrollUpButton = document.getElementById('scrollUp');

function scrollFunction() {
  if (
    document.body.scrollTop > sticky ||
    document.documentElement.scrollTop > sticky
  ) {
    scrollUpButton.style.display = 'block'
  } else {
    scrollUpButton.style.display = 'none'
  }
}

/** When the user clicks on the button, scroll to the top of the document. */
function topFunction() {
  document.body.scrollTop = 0 // for Safari
  document.documentElement.scrollTop = 0 // for Chrome, Firefox, IE and Opera
}

/** Functional sticky navbar. */
window.onscroll = function () {
  myFunction()
  scrollFunction()
};

var navBar = document.querySelector("nav")
var services = document.querySelector("#screenshots")
var sticky = services.offsetTop

function myFunction() {
  if (window.pageYOffset > sticky) {
    navBar.classList.add('sticky')
  } else {
    navBar.classList.remove('sticky')
  }
}

$(document).ready(function () {
  // preloader
  document.querySelector('.preloader').classList.add('opacity-0')
  setTimeout(function () {
    document.querySelector('.preloader').style.display = 'none'
  }, 1000)
  // nice select initialization
  $('select').niceSelect()
});

/** Navbar open function on mobile menu. */
function openNav() {
  $('#myNav').css('width', '100%')
}

/** Navbar close function on mobile menu. */
function closeNav() {
  $('#myNav').css('width', '0')
}
