console.log("auth.js đã được load!");
const API_BASE = 'http://localhost:8080';
//const API_URL = "http://localhost:8080";
const LOGIN_API    = API_BASE + '/api/auth/login';
const REGISTER_API = API_BASE + '/api/auth/register';
const USER_API     = API_BASE + '/api/user/profile';
const ADMIN_API    = API_BASE + '/api/admin/dashboard';

function saveToken(token, username = 'User') {
    localStorage.setItem('token', token);
    localStorage.setItem('username', username);
    Swal.fire({
        icon: 'success',
        title: 'Đăng nhập thành công!',
        text: `Xin chào ${username}!`,
        timer: 2000
    });
}

window.doLogin = async function(username, password) {
    try {
        const res = await fetch(LOGIN_API, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Accept': 'application/json'
            },
            body: JSON.stringify({ username, password })
        });

        const data = await res.json();

        if (res.ok) {
            saveToken(data.token, username);
            setTimeout(() => location.href = 'dashboard.html', 1000);
        } else {
            Swal.fire('Lỗi', data.message || 'Sai tài khoản/mật khẩu', 'error');
        }
    } catch (err) {
        Swal.fire('Lỗi kết nối', 'Backend đang tắt?', 'error');
    }
};

async function callSecuredApi(url, successMsg = 'Thành công!') {
    const token = localStorage.getItem('token');
    if (!token) {
        Swal.fire('Chưa đăng nhập', 'Vui lòng đăng nhập trước', 'warning');
        return;
    }

    try {
        const res = await fetch(url, {
            headers: {
                'Authorization': 'Bearer ' + token
            }
        });
        const text = await res.text();
        const alertType = res.ok ? 'success' : 'error';
        const title = res.ok ? successMsg : 'Lỗi ' + res.status;
        Swal.fire(title, text || res.statusText, alertType);
    } catch (err) {
        Swal.fire('Lỗi kết nối', 'Không thể kết nối tới server', 'error');
    }
}


function logout() {

    localStorage.removeItem('token');
    localStorage.removeItem('username');
    Swal.fire('Đã đăng xuất!', '', 'info').then(() => {
        window.location.href = 'login.html';
    });
}


window.loginAdmin = function() {
    console.log("Bắt đầu gọi loginAdmin!");
    window.doLogin('admin','123456');
}
window.loginUser  = function() {
    console.log("Bắt đầu gọi loginUser!");
    window.doLogin('user01','123456');
}


function testUserArea() { callSecuredApi(USER_API, 'Bạn đã vào khu vực User'); }
function testAdminArea() { callSecuredApi(ADMIN_API, 'Chào mừng Admin!'); }