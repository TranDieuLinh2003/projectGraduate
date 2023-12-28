

function validateFormRated(event) {
    // Lấy các giá trị từ các trường dữ liệu

    var lineSeat = document.getElementById('inputNumber').value;
    var lineSeatErr = document.getElementById('lineSeatErr');



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
    // Đặt các biến kiểm tra
    let isValid = true;

    // Kiểm tra trường rỗng
    if (lineSeat.trim() === '') {
        lineSeatErr.textContent = 'Vui lòng nhập số hàng ghế!!!';
        isValid = false;
    } else {
        lineSeatErr.innerText = "";
    }


    // Nếu có bất kỳ lỗi nào, ngăn chặn sự kiện mặc định của form
    if (!isValid) {
        event.preventDefault();
    }
}

function showContinueButton() {
    document.getElementById("addSeat").style.display = "none";
    document.getElementById("addSeatExcel").style.display = "block";
    document.getElementById("showDivButton").style.display = "none";
    document.getElementById("showDivButtonQuayLai").style.display = "block";


}function showContinueButtonQuayLai() {
    document.getElementById("addSeat").style.display = "block";
    document.getElementById("addSeatExcel").style.display = "none";
    document.getElementById("showDivButton").style.display = "block";
    document.getElementById("showDivButtonQuayLai").style.display = "none";


}