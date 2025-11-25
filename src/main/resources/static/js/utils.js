const script = document.createElement('script');
script.src = 'https://cdn.jsdelivr.net/npm/sweetalert2@11';
script.onload = () => console.log('SweetAlert2 loaded');
document.head.appendChild(script);