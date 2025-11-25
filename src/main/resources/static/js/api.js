async function callUserApi() {
    const res = await fetch('/api/user/profile', {
        headers: { 'Authorization': 'Bearer ' + localStorage.getItem('token') }
    });
    const text = await res.text();
    document.getElementById('userResult').textContent = res.ok ? text : 'Lỗi: ' + res.status;
}

async function callAdminApi() {
    const res = await fetch('/api/admin/dashboard', {
        headers: { 'Authorization': 'Bearer ' + localStorage.getItem('token') }
    });
    const text = await res.text();
    document.getElementById('adminResult').textContent = res.ok ? text : 'Lỗi: ' + res.status + ' (403 = không có quyền)';
}