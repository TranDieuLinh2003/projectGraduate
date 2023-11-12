const seats = document.querySelectorAll(".row .seat:not(.occupied)");
const seatContainer = document.querySelector(".row-container");

// Adding selected class to only non-occupied seats on 'click'

seatContainer.addEventListener("click", function (e) {
    if (
        e.target.classList.contains("seat") &&
        !e.target.classList.contains("occupied")
    ) {
        e.target.classList.toggle("selected");
        updateSelectedCount();
    }
});
document.addEventListener('DOMContentLoaded', function () {
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
        if (seatElements[i].classList.contains('black')) {
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

// const seats = document.querySelectorAll(".row .seat:not(.occupied)");
// const seatContainer = document.querySelector(".row-container");
// let selectedSeats = JSON.parse(localStorage.getItem('selectedSeats')) || [];
//
// // Function to update selected seats count
// function updateSelectedCount() {
//     const selectedSeatsCount = document.querySelectorAll('.row .seat.selected').length;
//     // Update count
//     // ...
// }
//
// // Function to save selected seats to local storage
// function saveSelectedSeats() {
//     localStorage.setItem('selectedSeats', JSON.stringify(selectedSeats));
// }
//
// // Function to set seat color on page load
// function setSeatColor() {
//     seats.forEach((seat, index) => {
//         if (selectedSeats.includes(index)) {
//             seat.classList.add("selected");
//         } else {
//             seat.classList.remove("selected");
//         }
//     });
// }
//
// // Function to set seats color to "default"
// function setSeatsToDefault() {
//     seats.forEach((seat) => {
//         seat.classList.remove("selected");
//     });
//     selectedSeats = [];
//     saveSelectedSeats();
//     updateSelectedCount();
// }
//
// // Add event listener to seats
// seatContainer.addEventListener("click", function (e) {
//     if (
//         e.target.classList.contains("seat") &&
//         !e.target.classList.contains("occupied")
//     ) {
//         e.target.classList.toggle("selected");
//         const seatIndex = Array.from(seats).indexOf(e.target);
//
//         if (selectedSeats.includes(seatIndex)) {
//             selectedSeats = selectedSeats.filter((index) => index !== seatIndex);
//         } else {
//             selectedSeats.push(seatIndex);
//         }
//
//         updateSelectedCount();
//         saveSelectedSeats();
//     }
// });
//
// // Set seat color to "default" on page load
// setSeatColor();
//
// // Set seats color to "black" on page reload
// window.addEventListener("load", function () {
//     seats.forEach((seat, index) => {
//         if (selectedSeats.includes(index)) {
//             seat.classList.add("black");
//         } else {
//             seat.classList.remove("black");
//         }
//     });
// });
//
// // Automatically set seats color to "default" after 1 minute
// setTimeout(function () {
//     setSeatsToDefault();
// }, 60000);
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
myData.innerHTML = "<b>" + formattedTime + "</b>";



//chọn ghế
var seat = document.getElementsByClassName("seat");

Array.from(seat).forEach(function (seat) {
    seat.addEventListener("click", toggleSelection);
});

function toggleSelection() {
    var seatNumber = this.textContent;
    var seatNumber1 = this.textContent;
    var seatNumber2 = this.textContent;

    var seat = this;
    var selectedSeats = document.getElementById("chair");
    var existingSeats = selectedSeats.querySelectorAll("b");

    var selectedSeats1 = document.getElementById("tenghe");
    var existingSeats1 = selectedSeats1.querySelectorAll("p");

    var selectedSeats2 = document.getElementById("tenghe1");
    var existingSeats2 = selectedSeats2.querySelectorAll("p");


    var isAlreadySelected = false;
    var isAlreadySelected1 = false;
    var isAlreadySelected2 = false;


    Array.from(existingSeats).forEach(function (existingSeat) {
        if (existingSeat.textContent === seatNumber) {
            isAlreadySelected = true;
            existingSeat.remove(); // Remove seat from <b> tag if it already exists
        }
    });

    Array.from(existingSeats1).forEach(function (existingSeat) {
        if (existingSeat.textContent === seatNumber1) {
            isAlreadySelected1 = true;
            existingSeat.remove(); // Remove seat from <p> tag if it already exists
        }
    });

    Array.from(existingSeats2).forEach(function (existingSeat) {
        if (existingSeat.textContent === seatNumber2) {
            isAlreadySelected2 = true;
            existingSeat.remove(); // Remove seat from <p> tag if it already exists
        }
    });

    // Check if the seat is occupied
    if (seat.classList.contains('occupied')) {
        var occupiedSeats = document.getElementById("gia");
        occupiedSeats.textContent = ""; // Remove price if seat is occupied
        return; // Don't perform any actions for occupied seats
    }
    if (seat.classList.contains('black')) {
        var occupiedSeats = document.getElementById("gia");
        occupiedSeats.textContent = ""; // Remove price if seat is occupied
        return; // Don't perform any actions for occupied seats
    }

    if (!isAlreadySelected) {
        var newSeat = document.createElement("b");
        newSeat.textContent = seatNumber;
        selectedSeats.appendChild(newSeat); // Add the seat to the <b> tag if it doesn't already exist
    }

    if (!isAlreadySelected1) {
        var newSeat1 = document.createElement("p");
        newSeat1.textContent = seatNumber1;
        selectedSeats1.appendChild(newSeat1); // Add the seat to the <p> tag if it doesn't already exist
    }

    if (!isAlreadySelected2) {
        var newSeat2 = document.createElement("p");
        newSeat2.textContent = seatNumber2;
        selectedSeats2.appendChild(newSeat2); // Add the seat to the <p> tag if it doesn't already exist
    }

    if (!isAlreadySelected2) {
        var seatInput = document.querySelector('input[name="seat"]');
        seatInput.value = seatNumber2;
        selectedSeats2.appendChild(seatInput); // Thêm ghế vào thẻ <p> nếu nó chưa tồn tại
        var allData = selectedSeats2.textContent; // Lấy tất cả dữ liệu trong selectedSeats2
        allData1 = allData.replace(/\n/g, ",");
        seatData = allData1.replace(/\s*,\s*/g, ",");
        seatData1 = seatData.replace(/,+/g, ",");
        document.querySelector('input[name="seat"]').value = seatData1; // Gán tất cả dữ liệu vào thẻ input ẩn
    }
    if (selectedSeats2.children.length === 0) {
        // Clear the input value if no seat is selected
        var seatInput = document.querySelector('input[name="seat"]');
        seatInput.value = "";
    }else {
        var seatInput = document.querySelector('input[name="seat"]');
        seatInput.value = seatNumber2;
        selectedSeats2.appendChild(seatInput); // Thêm ghế vào thẻ <p> nếu nó chưa tồn tại
        var allData = selectedSeats2.textContent; // Lấy tất cả dữ liệu trong selectedSeats2
        allData1 = allData.replace(/\n/g, ",");
        seatData = allData1.replace(/\s*,\s*/g, ",");
        seatData1 = seatData.replace(/,+/g, ",");
        document.querySelector('input[name="seat"]').value = seatData1;
    }

    if (!isAlreadySelected1) {
        var seatInput = document.querySelector('input[name="seatseat"]');
        seatInput.value = seatNumber1;
        selectedSeats1.appendChild(seatInput); // Thêm ghế vào thẻ <p> nếu nó chưa tồn tại
        var allData = selectedSeats1.textContent; // Lấy tất cả dữ liệu trong selectedSeats2
        allData1 = allData.replace(/\n/g, ",");
        seatData = allData1.replace(/\s*,\s*/g, ",");
        seatData1 = seatData.replace(/,+/g, ",");
        document.querySelector('input[name="seatseat"]').value = seatData1; // Gán tất cả dữ liệu vào thẻ input ẩn
    }
    if (selectedSeats1.children.length === 0) {
        // Clear the input value if no seat is selected
        var seatInput = document.querySelector('input[name="seatseat"]');
        seatInput.value = "";
    }else {
        var seatInput = document.querySelector('input[name="seatseat"]');
        seatInput.value = seatNumber2;
        selectedSeats1.appendChild(seatInput); // Thêm ghế vào thẻ <p> nếu nó chưa tồn tại
        var allData = selectedSeats1.textContent; // Lấy tất cả dữ liệu trong selectedSeats2
        allData1 = allData.replace(/\n/g, ",");
        seatData = allData1.replace(/\s*,\s*/g, ",");
        seatData1 = seatData.replace(/,+/g, ",");
        document.querySelector('input[name="seatseat"]').value = seatData1;
    }
}


// chọn trang

document.getElementById('showDivButton').addEventListener('click', function () {
    var div1 = document.getElementById('div1');
    var div2 = document.getElementById('div2');
    var div3 = document.getElementById('div3');
    var chair = document.getElementById("chair");
    var message = document.getElementById('modalView');
    if (chair.innerHTML === '') {
        message.style.display = "block";
        div1.style.display = 'block';
        div2.style.display = 'none';

    } else {
        message.innerHTML = "";
        div1.style.display = 'none';
        div2.style.display = 'block';
        document.getElementById("comeBack").style.display = "block";
        document.getElementById("showDivButton").style.display = "none";
        document.getElementById("showDivThanhToan").style.display = "block";
    }
});

document.getElementById('comeBack').addEventListener('click', function () {
    var div1 = document.getElementById('div1');
    var div2 = document.getElementById('div2');
    var div3 = document.getElementById('div3');
    var div4 = document.getElementById('div4');
    var chair = document.getElementById("chair");
    var message = document.getElementById('modalView');

    message.innerHTML = ""; // Clear any error message
    div1.style.display = 'block'; // Show div1
    div2.style.display = 'none'; // Hide div2
    div3.style.display = 'none';
    div4.style.display = 'none';

    document.getElementById("comeBack").style.display = "none";
    document.getElementById("showDivButton").style.display = "block";
    document.getElementById("showDivThanhToan").style.display = "none";
});
document.getElementById('showDivThanhToan').addEventListener('click', function () {
    var div1 = document.getElementById('div1');
    var div2 = document.getElementById('div2');
    var div3 = document.getElementById('div3');
    var div4 = document.getElementById('div4');
    var split_frame_2 = document.getElementById('split_frame_2');
    var chair = document.getElementById("chair");
    var message = document.getElementById('modalView');

    var firstRadio = document.getElementById('thenoidia');
    var secondRadio = document.getElementById('quetma');
    if (firstRadio.checked) {
        // Display div for Thẻ nội dung địa chỉ
        message.innerHTML = ""; // Clear any error message
        div1.style.display = 'none'; // Show div1
        div2.style.display = 'none'; // Hide div2
        div3.style.display = 'block';
        div4.style.display = 'none';
        split_frame_2.style.display = 'none'

        let amount = document.getElementById("thanhtien").textContent;
        document.getElementById("amount").innerHTML = amount;
        let amountt = parseInt(amount.replace("₫", "").replace(".", ""));
        document.getElementsByName("amount")[0].value = amountt;

        let amount1 = document.getElementById("thanhtien").textContent;
        document.getElementById("amount1").innerHTML = amount1;
        let amountt1 = parseInt(amount1.replace("₫", "").replace(".", ""));
        document.getElementsByName("amount1")[0].value = amountt1;


        document.getElementById("comeBack").style.display = "block";
        document.getElementById("showDivButton").style.display = "none";
        document.getElementById("showDivThanhToan").style.display = "none";
    } else if (secondRadio.checked) {
        message.innerHTML = ""; // Clear any error message
        div1.style.display = 'none'; // Show div1
        div2.style.display = 'none'; // Hide div2
        div3.style.display = 'none';
        div4.style.display = 'block';

        split_frame_2.style.display = 'none'

        let amount = document.getElementById("thanhtien").textContent;
        document.getElementById("amount").innerHTML = amount;
        let amountt = parseInt(amount.replace("₫", "").replace(".", ""));
        document.getElementsByName("amount")[0].value = amountt;
        let amount1 = document.getElementById("thanhtien").textContent;
        document.getElementById("amount1").innerHTML = amount1;
        let amountt1 = parseInt(amount1.replace("₫", "").replace(".", ""));
        document.getElementsByName("amount1")[0].value = amountt1;
        document.getElementById("comeBack").style.display = "block";
        document.getElementById("showDivButton").style.display = "none";
        document.getElementById("showDivThanhToan").style.display = "none";
        document.getElementById("cancel1").style.display = "none";
        document.getElementById("cancel2").style.display = "block";
        document.getElementById("thongtinthanhtoan").style.display = "none";
    }

});
document.getElementById('cancel2').addEventListener('click', function () {
    var div1 = document.getElementById('div1');
    var div2 = document.getElementById('div2');
    var div3 = document.getElementById('div3');
    var div4 = document.getElementById('div4');
    var split_frame_2 = document.getElementById('split_frame_2');
    var chair = document.getElementById("chair");
    var message = document.getElementById('modalView');

    message.innerHTML = ""; // Clear any error message
    div1.style.display = 'none'; // Show div1
    div2.style.display = 'none'; // Hide div2
    div3.style.display = 'none';
    div4.style.display = 'block';

    split_frame_2.style.display = 'none'

    let amount1 = document.getElementById("thanhtien").textContent;
    document.getElementById("amount1").innerHTML = amount1;
    let amountt1 = parseInt(amount1.replace("₫", "").replace(".", ""));
    document.getElementsByName("amount1")[0].value = amountt1;
    document.getElementById("comeBack").style.display = "block";
    document.getElementById("showDivButton").style.display = "none";
    document.getElementById("showDivThanhToan").style.display = "none";
    document.getElementById("cancel1").style.display = "block";
    document.getElementById("cancel2").style.display = "none";
    document.getElementById("thongtinthanhtoan").style.display = "block";


});
document.getElementById('cancel2').addEventListener('click', function () {
    var div1 = document.getElementById('div1');
    var div2 = document.getElementById('div2');
    var div3 = document.getElementById('div3');
    var div4 = document.getElementById('div4');
    var split_frame_2 = document.getElementById('split_frame_2');
    var chair = document.getElementById("chair");
    var message = document.getElementById('modalView');

    message.innerHTML = ""; // Clear any error message
    div1.style.display = 'none'; // Show div1
    div2.style.display = 'none'; // Hide div2
    div3.style.display = 'none';
    div4.style.display = 'block';

    split_frame_2.style.display = 'none'

    let amount1 = document.getElementById("thanhtien").textContent;
    document.getElementById("amount1").innerHTML = amount1;
    let amountt1 = parseInt(amount1.replace("₫", "").replace(".", ""));
    document.getElementsByName("amount1")[0].value = amountt1;
    document.getElementById("comeBack").style.display = "block";
    document.getElementById("showDivButton").style.display = "none";
    document.getElementById("showDivThanhToan").style.display = "none";
    document.getElementById("cancel1").style.display = "block";
    document.getElementById("cancel2").style.display = "none";
    document.getElementById("image").style.display = "block";
    document.getElementById("thongtinthanhtoan").style.display = "block";

});
var radioInputs = document.querySelectorAll('input[name="optradio"]');

// Add change event listener to each radio input
radioInputs.forEach(function(radioInput) {
    radioInput.addEventListener('change', function() {
        // Clear the 'checked' attribute from all radio inputs
        radioInputs.forEach(function(input) {
            input.removeAttribute('checked');
        });

        // Set the 'checked' attribute for the selected radio input
        if (this.checked) {
            this.setAttribute('checked', 'checked');
        }
    });
});
document.getElementById('comeBack2').addEventListener('click', function () {
    var div1 = document.getElementById('div1');
    var div2 = document.getElementById('div2');
    var div3 = document.getElementById('div3');
    var chair = document.getElementById("chair");
    var message = document.getElementById('modalView');
    var split_frame_2 = document.getElementById('split_frame_2');
    message.innerHTML = ""; // Clear any error message
    div1.style.display = 'block'; // Show div1
    div2.style.display = 'none'; // Hide div2
    div3.style.display = 'none';
    split_frame_2.style.display = 'block'
    document.getElementById("comeBack").style.display = "none";
    document.getElementById("showDivButton").style.display = "block";
    document.getElementById("showDivThanhToan").style.display = "none";
});
document.getElementById('comeBack3').addEventListener('click', function () {
    var div1 = document.getElementById('div1');
    var div2 = document.getElementById('div2');
    var div3 = document.getElementById('div3');
    var chair = document.getElementById("chair");
    var div4 = document.getElementById('div4');
    var message = document.getElementById('modalView');
    var split_frame_2 = document.getElementById('split_frame_2');
    message.innerHTML = ""; // Clear any error message
    div1.style.display = 'block'; // Show div1
    div2.style.display = 'none'; // Hide div2
    div3.style.display = 'none';
    div4.style.display = 'none';
    split_frame_2.style.display = 'block'
    document.getElementById("comeBack").style.display = "none";
    document.getElementById("showDivButton").style.display = "block";
    document.getElementById("showDivThanhToan").style.display = "none";
    document.getElementById("image").style.display = "none";
    document.getElementById("thongtinthanhtoan").style.display = "none";

});
// // tính tổng tiền
function sumFood() {
    const comboElements = document.getElementsByClassName("combo_tt");
    let total = 0;
    let selectedFoods = [];
    let selectedQuantity = [];
    let selectedPrice = [];
    for (let i = 0; i < comboElements.length; i++) {
        const quantityElement = comboElements[i].querySelector(".cart-quantity-input");
        const priceElement = comboElements[i].querySelector(".priceFood");
        const nameElement = comboElements[i].querySelector("#namefood");

        if (quantityElement && priceElement && nameElement) {
            const quantity = parseInt(quantityElement.value, 10);
            const priceString = priceElement.innerText;
            const name = nameElement.innerText;

            const price = parseFloat(priceString.replace("₫", "").replaceAll(".", "").replaceAll(",", "."));
            total += quantity * price;
            if (quantity > 0) {
                selectedFoods.push(name + "(" + quantity + ")"); // Lưu tên đồ ăn vào mảng khi số lượng lớn hơn 0
                selectedQuantity.push(quantity)
                selectedPrice.push(quantity * price)
            }
        }
    }
    const formattedTotalPrice = total.toLocaleString("vi-VN", {
        style: "currency",
        currency: "VND",
        minimumFractionDigits: 0,
        maximumFractionDigits: 9
    });
    const totalPriceElement = document.getElementById("totalPrice");
    const sumMoney = document.getElementById("tongtien").textContent;
    const intoMoney = document.getElementById("thanhtien");

    if (totalPriceElement) {
        totalPriceElement.innerText = formattedTotalPrice;
        const number1 = sumMoney;
        const number2 = formattedTotalPrice;
        const parsedNumber1 = parseFloat(number1.replace(/\./g, ""));
        const parsedNumber2 = parseFloat(number2.replace(/\./g, ""));
        const sumsum = parsedNumber1 + parsedNumber2;
        // var p = document.getElementById("amount");
        // var value = p.textContent

        document.getElementById("tongtiendoan").innerHTML = number2;
        document.getElementById("tongtiendoan1").innerHTML = number2;
        document.getElementsByName("priceFood")[0].value = number2;
        document.getElementsByName("priceFoodFood")[0].value = number2;

        const formattedSum = sumsum.toLocaleString("vi-VN", {style: "currency", currency: "VND"});

        intoMoney.innerText = formattedSum;


    }

    document.getElementById("doan").innerHTML = selectedFoods;// In ra mảng các đồ ăn đã chọn
    document.getElementById("doan1").innerHTML = selectedFoods;
    document.getElementsByName("foodd")[0].value = selectedFoods;
    document.getElementsByName("foodfood")[0].value = selectedFoods;



    const selectedNameInput = document.getElementById('selectedQuantity'); // Lấy thẻ input theo ID
    selectedNameInput.value = selectedQuantity.join(', ')

    const selectedPriceInput = document.getElementById('selectedPrice'); // Lấy thẻ input theo ID
    selectedPriceInput.value = selectedPrice.join(', ')

    const selectedNameInput1 = document.getElementById('selectedQuantity1'); // Lấy thẻ input theo ID
    selectedNameInput1.value = selectedQuantity.join(', ')

    const selectedPriceInput1 = document.getElementById('selectedPrice1'); // Lấy thẻ input theo ID
    selectedPriceInput1.value = selectedPrice.join(', ')

    var selectedInput = document.getElementById('selectedFood');
    var selectedIds = [];
    var selectedInput1 = document.getElementById('selectedFood1');
    var selectedIds1 = [];
    for (var i = 0; i < comboElements.length; i++) {
        var comboElement = comboElements[i];
        var quantityInput = comboElement.querySelector('.cart-quantity-input');
        var idElement = comboElement.querySelector('#namefood');

        if (quantityInput && 'value' in quantityInput && quantityInput.value > 0) {
            var id = idElement.getAttribute('value');
            console.log('ID:', id);
            selectedIds.push(id);
            selectedIds1.push(id);

        }
    }

    selectedInput.value = selectedIds.join();
    selectedInput1.value = selectedIds1.join();

}

//thanh toán


var rapchieu = document.getElementById("phong").textContent;
document.getElementById("phong2").innerHTML = rapchieu;
document.getElementById("phong3").innerHTML = rapchieu;

var ngaychieu = document.getElementById("ngay").textContent;
document.getElementById("ngaychieu").innerHTML = ngaychieu;
document.getElementById("ngaychieu1").innerHTML = ngaychieu;


var giochieu = document.getElementById("gio").textContent;
document.getElementById("giochieu").innerHTML = giochieu;
document.getElementById("giochieu1").innerHTML = giochieu;

var tenphim = document.getElementById("movie").textContent;
document.getElementById("orderInfo").innerHTML = tenphim;
document.getElementById("orderInfor").innerHTML = tenphim;


function getPhanTramGiam(checkbox) {
    var row = checkbox.parentNode.parentNode; // Lấy thẻ cha của checkbox (thẻ <tr>)
    var phantramgiamElement = row.querySelector(".phantramgiam"); // Tìm phần tử có lớp "phantramgiam" trong thẻ cha
    let tongtienElement = document.getElementById("tongtien");
    let tongtienData = tongtienElement.innerText;
    let giaTriFormatted = tongtienData.replace("₫", "").replace(".", "");

    let totalPriceElement = document.getElementById("totalPrice");
    let totalPriceData = totalPriceElement.innerText;
    let totalPriceFormatted = totalPriceData.replace("₫", "").replace(".", "");

    const number10 = giaTriFormatted;
    const number20 = totalPriceFormatted;
    const parsedNumber1 = parseFloat(number10.replace(/\./g, ""));
    const parsedNumber2 = parseFloat(number20.replace(/\./g, ""));
    let sum = parsedNumber1 + parsedNumber2
    if (checkbox.checked && phantramgiamElement) {
        let phantramgiam = phantramgiamElement.textContent;
        let giatrigiamchia = phantramgiam / 100;
        let phantram = sum * giatrigiamchia
        document.getElementById("tiendcgiam").innerHTML = "-" + phantram.toLocaleString("vi-VN", {
            style: "currency",
            currency: "VND"
        });
        document.getElementById("tiendcgiam1").innerHTML = "-" + phantram.toLocaleString("vi-VN", {
            style: "currency",
            currency: "VND"
        });
        document.getElementsByName("discount")[0].value ="-" + phantram.toLocaleString("vi-VN", {
            style: "currency",
            currency: "VND"
        });
        document.getElementsByName("discountcount")[0].value ="-" + phantram.toLocaleString("vi-VN", {
            style: "currency",
            currency: "VND"
        });
        let giatrigiam = sum - phantram;
        let giatriThanhTien = giatrigiam.toLocaleString("vi-VN", {style: "currency", currency: "VND"});


        document.getElementById("thanhtien").innerHTML = giatriThanhTien;
        document.getElementById("voucher").innerHTML = "-" + phantram.toLocaleString("vi-VN", {
            style: "currency",
            currency: "VND"
        });
        ;
        var row = checkbox.parentNode.parentNode;
        var promotionID = row.querySelector('td[value]').getAttribute('value');
        const selectedPromitionInput = document.getElementById('selectedPromition'); // Lấy thẻ input theo ID
        selectedPromitionInput.value = promotionID

        const selectedPromitionInput1 = document.getElementById('selectedPromition1'); // Lấy thẻ input theo ID
        selectedPromitionInput1.value = promotionID
    } else {
        let giatriThanhTien = sum.toLocaleString("vi-VN", {style: "currency", currency: "VND"});
        document.getElementById("thanhtien").innerHTML = giatriThanhTien;
        document.getElementById("tiendcgiam").innerHTML = 0
        document.getElementById("voucher").innerHTML = 0;
        const selectedPromitionInput = document.getElementById('selectedPromition'); // Lấy thẻ input theo ID
        selectedPromitionInput.value = ""
    }

    var checkboxes = document.querySelectorAll('input[type="checkbox"]');

    // Disable all checkboxes except the clicked one
    checkboxes.forEach(function (checkboxItem) {
        if (checkboxItem !== checkbox) {
            checkboxItem.disabled = checkbox.checked;
        }
    });


}

//tìm kiếm
function searchList() {
    var input, filter, comboItems, item, i;
    input = document.getElementById("searchInput");
    filter = input.value.toUpperCase();
    comboItems = document.getElementsByClassName("combo_tt");

    for (i = 1; i < comboItems.length; i++) {
        item = comboItems[i];
        nameFood = item.querySelector("b").innerText.toUpperCase();
        if (nameFood.indexOf(filter) > -1) {
            item.style.display = "";
        } else {
            item.style.display = "none";
        }
    }
}


let inputBox = document.querySelector('.input-box'),
    searchIcon = document.querySelector('.search'),
    closeIcon = document.querySelector('.close-icon');

// ---- ---- Open Input ---- ---- //
searchIcon.addEventListener('click', () => {
    inputBox.classList.add('open');
});
// ---- ---- Close Input ---- ---- //
closeIcon.addEventListener('click', () => {
    inputBox.classList.remove('open');
});

//lấy thông tin gửi snag controller để gửi về mail
var namePhim = document.getElementById("orderInfor").textContent;
document.getElementsByName("orderInfor")[0].value = namePhim;
var nameFilm = document.getElementById("orderInfo").textContent;
document.getElementsByName("nameFiml")[0].value = nameFilm;

var nameRoom = document.getElementById("phong3").textContent;
document.getElementsByName("room")[0].value = nameRoom;
var nameRoomm = document.getElementById("phong2").textContent;
document.getElementsByName("roomm")[0].value = nameRoomm;

var date = document.getElementById("ngaychieu1").textContent;
document.getElementsByName("date")[0].value = date;
var datedate = document.getElementById("ngaychieu").textContent;
document.getElementsByName("datedate")[0].value = datedate

var time = document.getElementById("giochieu1").textContent;
document.getElementsByName("time")[0].value = time;
var timetime = document.getElementById("giochieu").textContent;
document.getElementsByName("timetime")[0].value = timetime;




