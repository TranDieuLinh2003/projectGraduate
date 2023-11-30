const rowsPerPage1 = 5;
// Các hàng dữ liệu trong bảng
const tableRows = document.querySelectorAll('tbody tr');
// Tính số trang dựa trên số hàng dữ liệu
const totalPages1 = Math.ceil(tableRows.length / rowsPerPage1);
let currentPage1 = 1; // Trang hiện tại

showPage(1); // Hiển thị trang đầu tiên khi trang web được tải
// Tạo và thêm nút phân trang vào phần tử có lớp là "pagination"
const pagination = document.querySelector('.pagination');
updatePagination(currentPage1);

function updatePagination(currentPage1) {
    // pagination.innerHTML = ''; // Xóa hết các button cũ
    const maxButtons = 5; // Số lượng button tối đa được hiển thị
    const startPage = Math.max(1, currentPage1 - Math.floor(maxButtons / 2));
    const endPage = Math.min(totalPages1, startPage + maxButtons - 1);

    if (startPage > 1) {
        const previousPage = document.createElement('button');
        previousPage.innerText = '<';
        previousPage.addEventListener('click', function() {
            currentPage1 = Math.max(1, currentPage1 - 1); // Di chuyển đến trang trước đó
            showPage(currentPage1);
            updatePagination(currentPage1);
        });
        pagination.appendChild(previousPage);
    }

    for (let i = startPage; i <= endPage; i++) {
        const button = document.createElement('button');
        button.innerText = i;

        button.addEventListener('click', function() {
            currentPage1 = i;
            showPage(currentPage1);
            updatePagination(currentPage1);
        });
        if (i === currentPage1) {
            button.classList.add('active');
            button.style.backgroundColor = 'darkgray';
        }
        pagination.appendChild(button);
    }
    if (endPage < totalPages1) {
        const nextPage = document.createElement('button');
        nextPage.innerText = '>';
        nextPage.addEventListener('click', function() {
            currentPage1 = Math.min(totalPages1, currentPage1 + 1); // Di chuyển đến trang kế tiếp
            showPage(currentPage1);
            updatePagination(currentPage1);
        });
        pagination.appendChild(nextPage);
    }

}

// Hiển thị các hàng dữ liệu tương ứng với trang được chọn
function showPage(pageNumber) {
    const startIndex = (pageNumber - 1) * rowsPerPage1;
    const endIndex = startIndex + rowsPerPage1;
    tableRows.forEach((row, index) => {
        if (index >= startIndex && index < endIndex) {
            row.style.display = '';
        } else {
            row.style.display = 'none';
        }
    });
}