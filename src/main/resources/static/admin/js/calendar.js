const daysContainer = document.querySelector(".days"),
    nextBtn = document.querySelector(".next-btn"),
    prevBtn = document.querySelector(".prev-btn"),
    todayBtn = document.querySelector(".today-btn"),
    mouth = document.querySelector(".mouth")

const mouths =[
    'Tháng 1',
    'Tháng 2',
    'Tháng 3',
    'Tháng 4',
    'Tháng 5',
    'Tháng 6',
    'Tháng 7',
    'Tháng 8',
    'Tháng 9',
    'Tháng 10',
    'Tháng 11',
    'Tháng 12'
];
const day = [
    'Thứ 2',
    'Thứ 3',
    'Thứ 4 ',
    'Thứ 5',
    'Thứ 6',
    'Thứ 7',
    'Chủ nhật'
]

const date = new Date();

let currentMouth = date.getMonth();

let currentYear = date.getFullYear();

function renderCalendar(){
    date.setDate(1);
    const firstDay = new Date(currentYear, currentMouth,1);
    const lastDay = new Date(currentYear, currentMouth+1, 0);
    const lastDayIndex = lastDay.getDay();
    const lastDayDate = lastDay.getDate();
    const prevLastDay = new Date(currentYear, currentMouth, 0);
    const prevLastDayDate = prevLastDay.getDate();
    const nectDays = 7 - lastDayIndex - 1;

    mouth.innerHTML = `${mouths[currentMouth]} ${currentYear}`;

    // update days
    let days = "";

    for(let x= firstDay.getDay(); x > 0; x--){
        days += `<div class="day prev">${prevLastDayDate - x +1}</div>`
    }

    for(let i = 1; i <=lastDayDate; i++){
        if(i === new Date().getDate() &&
            currentMouth === new Date().getMonth() &&
            currentYear === new Date().getFullYear()
        ){
            days += `<div class="day today" data-bs-toggle="modal" data-bs-target="#exampleModal">${i}</div>`
        }else{
            days += `<div class="day" data-bs-toggle="modal" data-bs-target="#exampleModal">${i}</div>`

        }
    }

    // next mouth days
    for(let j = 1; j <= nectDays; j++){
        days += `<div class="day next">${j}</div>`
    }
    hideTodayBtn();
    daysContainer.innerHTML = days;
}
renderCalendar() ;

nextBtn.addEventListener("click", ()=>{
    currentMouth++;
    if(currentMouth > 11){
        currentMouth = 0;
        currentYear ++;
    }else{
        renderCalendar();
    }
});
prevBtn.addEventListener("click", ()=>{
    currentMouth--;
    if(currentMouth < 0){
        currentMouth = 11;
        currentYear --;
    }else{
        renderCalendar();
    }
})

todayBtn.addEventListener("click", ()=>{
    currentMouth = date.getMonth();
    currentYear = date.getFullYear();
    renderCalendar();
});

function hideTodayBtn(){
    if(
        currentMouth === new Date().getMonth() &&
        currentYear === new Date().getFullYear()
    ){
        todayBtn.style.display = "none";
    }else{
        todayBtn.style.display = "flex";
    }
}