function validateFormRoom(event) {
    // Lấy các giá trị từ các trường dữ liệu
    const quantitys = document.getElementById('quantity').value;

    const quantityError = document.getElementById('quantityErr');
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
    if (quantitys.trim() === '') {
        quantityError.textContent = 'Số lượng phòng không được để trống !';
        isValid = false;
    } else if(isNaN(quantitys) || quantitys <= 0 || quantitys >13) {
        quantityError.textContent = 'Số lượng phòng phải lớn hơn 0 và nhỏ hơn 13 !';
        isValid = false;
    } else {
        movieDurationError.textContent = '';
    }

    // Nếu có bất kỳ lỗi nào, ngăn chặn sự kiện mặc định của form
    if (!isValid) {
        event.preventDefault(); // This line prevents the default behavior without any visible indication
    }
}

function updateFormRoom(event){
    var nameRoom = document.getElementById('nameRoom').value;
    var nameRoomErr = document.getElementById('nameRoomErr');
    var descriptionRoom = document.getElementById('descriptionRoom').value;
    var descriptionRoomErr = document.getElementById('descriptionRoomErr');
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
    if(nameRoom.trim() === ''){
        nameRoomErr.textContent = "Tên phòng không được trống !";
        isValid = false;
    }else {
        nameRoomErr.innerText = "";
    }if(descriptionRoom.trim() === ''){
        descriptionRoomErr.textContent = "Mô tả trống";
        isValid = false
    }else {
        descriptionRoomErr.innerText = "";
    }
    if (!isValid) {
        event.preventDefault(); // This line prevents the default behavior without any visible indication
    }
}