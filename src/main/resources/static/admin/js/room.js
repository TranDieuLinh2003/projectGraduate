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
    } else if(isNaN(quantitys) || +quantitys <= 0) {
        quantityError.textContent = 'Số lượng phòng phải lớn hơn 0 !';
        isValid = false;
    } else {
        movieDurationError.textContent = '';
    }

    // Nếu có bất kỳ lỗi nào, ngăn chặn sự kiện mặc định của form
    if (!isValid) {
        Toast.fire({
            icon: "error",
            title: "Thêm phòng thất bại"
        });
        document.getElementById('loading-overlay').style.display = 'none';
        event.preventDefault();
    }else {
            Toast.fire({
                icon: "success",
                title: "Thêm phòng thành công"
            })
        document.getElementById('loading-overlay').style.display = 'flex';
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
        Toast.fire({
            icon: "error",
            title: "Sửa phòng thất bại"
        });
        document.getElementById('loading-overlay').style.display = 'none';
        event.preventDefault();
    }else {
        Toast.fire({
            icon: "success",
            title: "Sửa phòng thành công"
        })
        document.getElementById('loading-overlay').style.display = 'flex';
    }
}