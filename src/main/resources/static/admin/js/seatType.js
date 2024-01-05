
function validateCinema(event){
    var nameCinema = document.getElementById('name').value;
    var nameCinemaErr = document.getElementById('nameErr');
    var addressCinema = document.getElementById('surcharge').value;
    var addressCinemaErr = document.getElementById('surchargeErr');
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

    if(nameCinema.trim() === ''){
        nameCinemaErr.textContent = "Không được để trống !";
        isValid = false;
    }else {
        nameCinemaErr.innerText = "";
    }
    if(addressCinema.trim() === ''){
        addressCinemaErr.textContent = "Không được để trống !";
        isValid = false;
    }else {
        addressCinemaErr.innerText = "";
    }
    if (!isValid) {
        event.preventDefault(); // This line prevents the default behavior without any visible indication
    }
}