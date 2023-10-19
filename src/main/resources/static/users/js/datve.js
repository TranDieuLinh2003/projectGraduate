const seats = document.querySelectorAll(".row .seat:not(.occupied)");
const seatContainer = document.querySelector(".row-container");
const count = document.getElementById("count");
const total = document.getElementById("total");
const movieSelect = document.getElementById("movie");

// Another Approach



populateUI();

let ticketPrice = +movieSelect.value;

// Save selected movie index and price
function setMovieData(movieIndex, moviePrice) {
    localStorage.setItem("selectedMovieIndex", movieIndex);
    localStorage.setItem("selectedMoviePrice", moviePrice);
}


// Get data from localstorage and populate
function populateUI() {
    const selectedSeats = JSON.parse(localStorage.getItem("selectedSeats"));

    if (selectedSeats !== null && selectedSeats.length > 0) {
        seats.forEach(function(seat, index) {
            if (selectedSeats.indexOf(index) > -1) {
                seat.classList.add("selected");
            }
        });
    }

    const selectedMovieIndex = localStorage.getItem("selectedMovieIndex");

    if (selectedMovieIndex !== null) {
        movieSelect.selectedIndex = selectedMovieIndex;
    }
}

// Movie select event

movieSelect.addEventListener("change", function(e) {
    ticketPrice = +movieSelect.value;
    setMovieData(e.target.selectedIndex, e.target.value);
    updateSelectedCount();
});

// Adding selected class to only non-occupied seats on 'click'

seatContainer.addEventListener("click", function(e) {
    if (
        e.target.classList.contains("seat") &&
        !e.target.classList.contains("occupied")
    ) {
        e.target.classList.toggle("selected");
        updateSelectedCount();
    }
});

// Initial count and total rendering
// updateSelectedCount();

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
// lấy ra ngày
var myData = document.getElementById("ngay").innerHTML;
var dateTime = new Date(myData);
var day = dateTime.getDate(); // Lấy ngày trong tháng (1-31)
var formattedDay = String(day).padStart(2, '0')
var month = dateTime.getMonth() + 1; // Lấy tháng (0-11), cộng thêm 1 vì tháng bắt đầu từ 0
var formattedMonth = String(month).padStart(2, '0');
var year = dateTime.getFullYear(); // Lấy năm (đầy đủ, ví dụ: 2023)
var formattedDate = formattedDay + "/" + formattedMonth + "/" + year;
document.getElementById("ngay").innerHTML = formattedDate;
myData.innerHTML = "<b>" + formattedDate + "</b>";

//    lấy ra giờ
var myData = document.getElementById("gio").innerHTML;
var dateTime = new Date(myData);
var hour = dateTime.getHours(); // Lấy giờ (0-23)
var formattedHour = String(hour).padStart(2, '0');
var minute = dateTime.getMinutes();
var formattedMinute = String(minute).padStart(2, '0');
var formattedTime = formattedHour + ":" + formattedMinute;
document.getElementById("gio").innerHTML = formattedTime;
// myData.innerHTML = "<b>" + formattedTime + "</b>";


//chọn ghế
var seat = document.getElementsByClassName("seat");

Array.from(seat).forEach(function (seat) {
    seat.addEventListener("click", toggleSelection);
});

function toggleSelection() {
    var seatNumber = this.textContent;
    var selectedSeats = document.getElementById("chair");
    var existingSeats = selectedSeats.querySelectorAll("b");

    var isAlreadySelected = false;
    Array.from(existingSeats).forEach(function (existingSeat) {
        if (existingSeat.textContent === seatNumber) {
            isAlreadySelected = true;
            existingSeat.remove(); // Xóa ghế khỏi thẻ <b> nếu đã tồn tại
        }
    });

    if (!isAlreadySelected) {
        var newSeat = document.createElement("b");
        newSeat.textContent = seatNumber;
        selectedSeats.appendChild(newSeat); // Thêm ghế vào thẻ <b> nếu chưa tồn tại
    }
}


document.addEventListener('DOMContentLoaded', function() {
    setSeatsColor('default');
});

// Function to set the color of all seats
function setSeatsColor(color) {
    // Get all the seat elements
    var seatElements = document.getElementsByClassName('seat');

    // Loop through each seat element
    for (var i = 0; i < seatElements.length; i++) {
        // Check if the seat is already occupied
        if (seatElements[i].classList.contains('occupied')) {
            continue; // Skip occupied seats
        }

        // Check if the seat is selected or default
        if (seatElements[i].classList.contains('selected')) {
            seatElements[i].classList.remove('selected');
        } else {
            seatElements[i].className = 'seat ' + color;
        }
    }
}
// chọn trang
// document.getElementById('showDivButton').addEventListener('click', function () {
//     var div1 = document.getElementById('div1');
//     var div2 = document.getElementById('div2');
//     var chair = document.getElementById("chair");
//     var message = document.getElementById('modalView');
//     if (chair.innerHTML === '') {
//         // message.innerHTML = "Thông báo: Vui lòng khách hàng chọn ghế";
//         message.style.display = "block";
//         div1.style.display = 'block';
//         div2.style.display = 'none';
//     } else {
//         message.innerHTML = "";
//         div1.style.display = 'none';
//         div2.style.display = 'block';
//         document.getElementById("comeBack").style.display = "block";
//     }
// });
document.getElementById('showDivButton').addEventListener('click', function () {
    var div1 = document.getElementById('div1');
    var div2 = document.getElementById('div2');
    var chair = document.getElementById("chair");
    var message = document.getElementById('modalView');
    if (chair.innerHTML === '') {
        // message.innerHTML = "Thông báo: Vui lòng khách hàng chọn ghế";
        message.style.display = "block";
        div1.style.display = 'block';
        div2.style.display = 'none';
    } else {
        message.innerHTML = "";
        div1.style.display = 'none';
        div2.style.display = 'block';
        document.getElementById("comeBack").style.display = "block";
    }
});

document.getElementById('comeBack').addEventListener('click', function () {
    var div1 = document.getElementById('div1');
    var div2 = document.getElementById('div2');
    var chair = document.getElementById("chair");
    var message = document.getElementById('modalView');

    // Save the selected chair information or other relevant data before going back to the div1
    // You can store it in a variable or use other appropriate methods

    message.innerHTML = ""; // Clear any error message
    div1.style.display = 'block'; // Show div1
    div2.style.display = 'none'; // Hide div2
    document.getElementById("comeBack").style.display = "none"; // Hide the "comeBack" button
});

// tính tổng tiền
function sumFood() {
    const comboElements = document.getElementsByClassName("combo_tt");
    let total = 0;

    for (let i = 0; i < comboElements.length; i++) {
        const quantityElement = comboElements[i].querySelector(".cart-quantity-input");
        const priceElement = comboElements[i].querySelector(".priceFood");

        if (quantityElement && priceElement) {
            const quantity = parseInt(quantityElement.value, 10);
            const priceString = priceElement.innerText;

            const price = parseFloat(priceString.replace("₫", "").replaceAll(".", "").replaceAll(",", "."));
            total += quantity * price;
        }
    }

    const formattedTotalPrice = total.toLocaleString("vi-VN", {
        style: "currency",
        currency: "VND",
        minimumFractionDigits: 0,
        maximumFractionDigits: 9
    });
    const totalPriceElement = document.getElementById("totalPrice");
    var sumMoney = document.getElementById("tongtien").textContent;
    const intoMoney = document.getElementById("thanhtien");
    if (totalPriceElement) {
        totalPriceElement.innerText =  formattedTotalPrice;
        var number1 = sumMoney;
        var number2 = formattedTotalPrice;
        var parsedNumber1 = parseFloat(number1.replace(/\./g, ""));
        var parsedNumber2 = parseFloat(number2.replace(/\./g, ""));
        var sumsum = parsedNumber1 + parsedNumber2;
        var formattedSum = sumsum.toLocaleString("vi-VN", { style: "currency", currency: "VND" });

        intoMoney.innerText = formattedSum;

    }
}