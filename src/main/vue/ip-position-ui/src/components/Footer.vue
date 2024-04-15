<template>
    <footer class="footer">
        <a href="/" class="logo-name">
            {{ logo_name }}
        </a>
        <p class="footer-copyright">
            © {{ logo_name }} 2023-2024
        </p>
        <a class="footer-license" href="https://github.com/TimoxGagarin/ip-position/blob/main/LICENSE.md">Лицензия</a>
    </footer>
</template>

<style scoped>
.footer{
    margin-top: 60px;
    padding-left: 10px;
    padding-right: 10px;
    padding-top: 20px;
    padding-bottom: 20px;
    background: black;
    display: flex;
    color: white;
    vertical-align: middle;
}

.footer-copyright{
    color: white;
    margin-left: 50px;
}

.footer-license{
    margin-left: 10px;
    color: white;
}

.logo-name{
    color: white;
    font-size: 26px;
    font-weight: bold;
    cursor: pointer;
    text-decoration: none;
    display: block;
}
</style>

<script>
export default{
    props: {
        logo_name: {
            type: String,
            required: true
        }
    }
}
</script>