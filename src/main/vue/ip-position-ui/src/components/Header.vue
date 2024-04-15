<template>
    <header class="header">
        <a href="/" class="logo-name">
            {{ logo_name }}
        </a>
    </header>
</template>

<style scoped>
.header{
    margin-bottom: 60px;
    padding-left: 10px;
    padding-right: 10px;
    padding-top: 20px;
    padding-bottom: 20px;
    background: black;
}

.logo-name{
    color: white;
    font-size: 26px;
    font-weight: bold;
    cursor: pointer;
    text-decoration: none;
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