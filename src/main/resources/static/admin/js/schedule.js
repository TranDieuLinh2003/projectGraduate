///
const selectBtn = document.querySelector(".select-btn"),
    items = document.querySelectorAll(".item");
const selectBtnRoom = document.querySelector(".select-btn-room"),
    itemsRoom = document.querySelectorAll(".itemRoom");

// items.forEach(item => {
//     item.addEventListener("click", () => {
//         item.classList.toggle("checked");
//
//         let checked = document.querySelectorAll(".checked"),
//             btnText = document.querySelector(".btn-text");
//
//         if(checked && checked.length > 0){
//             btnText.innerText = `${checked.length} Phim đã chọn`;
//         }else{
//             btnText.innerText = "Danh sách phim";
//         }
//     });
// })
///
selectBtn.addEventListener("click", () => {
    selectBtn.classList.toggle("open");
});
selectBtnRoom.addEventListener("click", () => {
    selectBtnRoom.classList.toggle("open");
});
let selectedItems = []; // Mảng lưu trữ các phần tử đã chọn

items.forEach(item => {
    item.addEventListener("click", () => {
        item.classList.toggle("checked");
    });
});

itemsRoom.forEach(item => {
    item.addEventListener("click", () => {
        item.classList.toggle("checked");
    });
});

var selectedMovies = [];

function showSelectedMovies(checkbox) {
    var movieName = checkbox.parentNode.querySelector("span").textContent;

    if (checkbox.checked) {
        selectedMovies.push(movieName);
    } else {
        var index = selectedMovies.indexOf(movieName);
        if (index !== -1) {
            selectedMovies.splice(index, 1);
        }
    }

    var selectedMovieList = document.getElementById("selectedMovieList");
    selectedMovieList.innerHTML = "";

    for (var i = 0; i < selectedMovies.length; i++) {
        var listItem = document.createElement("li");
        listItem.textContent = (i + 1) + ". " + selectedMovies[i];
        selectedMovieList.appendChild(listItem);
    }

    var checkboxes = document.getElementsByName("checkboxItem");
    var selectedMoviesInput = [];

    for (var i = 0; i < checkboxes.length; i++) {
        if (checkboxes[i].checked) {
            selectedMoviesInput.push(checkboxes[i].value);
        }
    }

    var inputElement = document.querySelector('input[name="listMovieChecked"]');
    inputElement.value = selectedMoviesInput.join(',');

    console.log(selectedMoviesInput);
}

var selectedRoom = [];

function showSelectedRoom(checkbox) {
    var roomName = checkbox.parentNode.querySelector("span").textContent;

    if (checkbox.checked) {
        selectedRoom.push(roomName);
    } else {
        var index = selectedRoom.indexOf(roomName);
        if (index !== -1) {
            selectedRoom.splice(index, 1);
        }
    }

    var selectedRoomList = document.getElementById("selectedRoomList");
    selectedRoomList.innerHTML = "";

    for (var i = 0; i < selectedRoom.length; i++) {
        var listItem = document.createElement("li");
        listItem.textContent = (i + 1) + ". " + selectedRoom[i];
        selectedRoomList.appendChild(listItem);
    }

    var checkboxes = document.getElementsByName("checkboxItemRoom");
    var selectedRoomInput = [];

    for (var i = 0; i < checkboxes.length; i++) {
        if (checkboxes[i].checked) {
            selectedRoomInput.push(checkboxes[i].value);
        }
    }

    var inputElement = document.querySelector('input[name="listRoomChecked"]');
    inputElement.value = selectedRoomInput.join(',');

    console.log(selectedRoomInput);
}


function validateSchedule(event) {
    var startTime = document.getElementById('startTime').value;
    var endTime = document.getElementById('endTime').value;
    var danhSachPhim = document.getElementById('danhSachPhim').value;
    var startTimeErr = document.getElementById('startTimeErr');
    var endTimeErr = document.getElementById('endTimeErr');
    var danhSachPhimErr = document.getElementById('danhSachPhimErr');
    const Toast = Swal.mixin({
        toast: true,
        position: "top-end",
        showConfirmButton: false,
        timer: 3000,
        timerProgressBar: true,
        didOpen: (toast) => {
            toast.onmouseenter = Swal.stopTimer;
            toast.onmouseleave = Swal.resumeTimer;
        }
    });
    var isValid = true;

    if (startTime.trim() === '') {
        startTimeErr.textContent = "Ngày bắt đầu không để trống !";
        isValid = false;
    } else {
        startTimeErr.innerText = "";
    }
    if (endTime.trim() === '') {
        endTimeErr.textContent = "Ngày kết thúc không được trống !";
        isValid = false;
    } else if (new Date(endTime) <= new Date(startTime)) {
        endTimeErr.textContent = "Ngày kết thúc phải lớn hơn ngày bắt đầu !";
        isValid = false;
    } else {
        endTimeErr.innerText = "";
    }
    if (danhSachPhim.trim() === '') {
        danhSachPhimErr.textContent = "Phải chọn ít nhất 1 phim !";
        isValid = false;
    } else {
        danhSachPhimErr.innerText = "";
    }
    if (!isValid) {
        Toast.fire({
            icon: "error",
            title: "Thêm lịch chiếu thất bại"
        });
        document.getElementById('loading-overlay').style.display = 'none';
        event.preventDefault();
    }else {
        Toast.fire({
            icon: "success",
            title: "Thêm lịch chiếu thành công"
        });
        document.getElementById('loading-overlay').style.display = 'flex';
    }
}

function updateSchedule(event){

    var startTimeUpdate = document.getElementById('startTimeUpdate').value;
    var startTimeUpdateErr = document.getElementById('startTimeUpdateErr');
    var endDateUpdate = document.getElementById('endTimeUpdate').value;
    var endDateUpdateErr = document.getElementById('endTimeUpdateErr');
    var priceLichChieuErr = document.getElementById('priceLichChieuErr');
    var priceLichChieu = document.getElementById('priceLichChieu').value;
    const Toast = Swal.mixin({
        toast: true,
        position: "top-end",
        showConfirmButton: false,
        timer: 3000,
        timerProgressBar: true,
        didOpen: (toast) => {
            toast.onmouseenter = Swal.stopTimer;
            toast.onmouseleave = Swal.resumeTimer;
        }
    });
    var isValid = true;
    if (startTimeUpdate.trim() === '') {
        startTimeUpdateErr.textContent = "Ngày bắt đầu không để trống !";
        isValid = false;
    } else {
        startTimeUpdateErr.innerText = "";
    }
    if (endDateUpdate.trim() === '') {
        endDateUpdateErr.textContent = "Ngày kết thúc không được trống !";
        isValid = false;
    } else if (new Date(endDateUpdate) <= new Date(startTimeUpdate)) {
        endDateUpdateErr.textContent = "Ngày kết thúc phải lớn hơn ngày bắt đầu !";
        isValid = false;
    } else {
        endDateUpdateErr.innerText = "";
    }
    if(priceLichChieu.trim() === ''){
        priceLichChieuErr.textContent = "Giá không được để trống !";
        isValid = false;
    }else if(isNaN(priceLichChieu) || priceLichChieu < 0){
        priceLichChieuErr.textContent = "Giá không được âm !";
        isValid = false;
    }else {
        priceLichChieuErr.innerText = "";
    }
    if (!isValid) {
        Toast.fire({
            icon: "error",
            title: "Sửa lịch chiếu thất bại"
        });
        document.getElementById('loading-overlay').style.display = 'none';
        event.preventDefault();
    }else {
        Toast.fire({
            icon: "success",
            title: "Thêm lịch chiếu thành công"
        });
        document.getElementById('loading-overlay').style.display = 'flex';
    }

}
// var selects = document.querySelectorAll('select');
//
// selects.forEach(function(select) {
//     select.addEventListener('focus', function() {
//         select.size = 5;
//         select.classList.add('fadeIn');
//         select.classList.remove('fadeOut');
//         select.style.backgroundColor = '#FFF';
//     });
//
//     select.addEventListener('blur', function() {
//         select.size = 1;
//         select.classList.add('fadeOut');
//         select.classList.remove('fadeIn');
//         select.style.backgroundColor = '#FFF';
//     });
//
//     select.addEventListener('change', function() {
//         select.size = 1;
//         select.blur();
//         select.style.backgroundColor = '#FFF';
//     });
//
//     select.addEventListener('mouseover', function() {
//         if(select.size === 1) {
//             select.style.backgroundColor = 'rgb(247, 247, 247)';
//         }
//     });
//
//     select.addEventListener('mouseout', function() {
//         if(select.size === 1) {
//             select.style.backgroundColor = '#FFF';
//         }
//     });
// });

var currentDate = new Date();
var currentDay = currentDate.getDate();
var currentMonth = currentDate.getMonth() + 1;
var currentYear = currentDate.getFullYear();

var sevenDays = [];

for (var i = 0; i < 7; i++) {
    var nextDay = new Date(currentYear, currentMonth - 1, currentDay + i);

    var dateArray = [nextDay.getDate(), nextDay.getMonth() + 1, nextDay.getFullYear()]; // Mảng chứa năm, tháng và ngày
    dateArray = dateArray.map(function (item) { // Chuyển đổi các phần tử trong mảng thành chuỗi và kiểm tra xem có cần thêm số 0 ở phía trước không
        return (item < 10 ? "0" : "") + item;
    });

    var dateString = dateArray.join('/'); // Xây dựng lại chuỗi ngày tháng

    sevenDays.push(dateString);
}

var selectElement = document.getElementById('startAt');

for (var j = 0; j < sevenDays.length; j++) {
    var optionElement = document.createElement('option');
    optionElement.value = sevenDays[j];
    optionElement.textContent = sevenDays[j];
    selectElement.appendChild(optionElement);
}

// const myData = document.getElementById("gio").textContent;
// var dateTime = new Date(myData);
// var hour = dateTime.getHours(); // Lấy giờ (0-23)
// var formattedHour = String(hour).padStart(2, '0');
// var minute = dateTime.getMinutes();
// var formattedMinute = String(minute).padStart(2, '0');
// var formattedTime = formattedHour + ":" + formattedMinute;
// document.getElementById("gio").innerHTML = formattedTime;
// var gioElements = document.querySelectorAll("#gio");

// gioElements.forEach(function (element) {
//     var myData = element.textContent;
//     var dateTime = new Date(myData);
//     var hour = dateTime.getHours(); // Lấy giờ (0-23)
//     var formattedHour = String(hour).padStart(2, '0');
//     var minute = dateTime.getMinutes();
//     var formattedMinute = String(minute).padStart(2, '0');
//     var formattedTime = formattedHour + ":" + formattedMinute;
//     element.innerHTML = formattedTime;
// })