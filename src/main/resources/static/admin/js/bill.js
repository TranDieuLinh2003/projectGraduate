function xacNhanHoaDon(event){
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
    if (!isValid) {
        Toast.fire({
            icon: "error",
            title: "Xác nhận hóa đơn thất bại"
        });
        document.getElementById('loading-overlay').style.display = 'none';
        event.preventDefault();
    } else {
        Toast.fire({
            icon: "success",
            title: "Xác nhận hóa đơn thành công"
        }).then(function () {
                window.location.reload();
            }
        );
        document.getElementById('loading-overlay').style.display = 'flex';
    }
}