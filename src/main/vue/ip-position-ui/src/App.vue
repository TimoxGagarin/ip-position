<script>
import axios from 'axios';
import Header from './components/Header.vue';
import Footer from './components/Footer.vue';
import MyMap from './components/MyMap.vue';

export default{
    components: {
        Header,
        Footer,
        MyMap
    },
    data() {
        return {
            logo_name: "IP-position",
            filter: {
                city: {
                    country: "",
                    countryCode: "",
                    region: "",
                    regionName: "",
                    name: "",
                    zip: ""
                },
                provider:{
                    internetServiceProvider: "",
                    organisation: "",
                    autonomousSystemName: ""
                },
                position: {
                    latitude: "",
                    longitude: ""
                },
                timeZone: "",
                ip: "",
            },
            to_add: {
                "ip": "",
                "city": {
                    "country": "",
                    "countryCode": "",
                    "region": "",
                    "regionName": "",
                    "name": "",
                    "zip": ""
                },
                "position": {
                    "latitude": "",
                    "longitude": "",
                },
                "timeZone": "",
                "provider": {
                    "internetServiceProvider": "",
                    "organisation": "",
                    "autonomousSystemName": ""
                }
            },
            updateId: "",
            deleteId: "",
            findError: "",
            addError: "",
            updateError: "",
            deleteError: "",
            info: null
        }
    },
    computed: {
        ipShow() {
            return "«" + this.filter.ip + "»"
        }
    },
    methods: {
        getIpInfo() {
            let new_dict = {...this.filter.provider, ...this.filter.city, ...this.filter.position, "ip": this.filter.ip, "timeZone": this.filter.timeZone};
            let queryString = Object.keys(new_dict)
            .map(key => {
                const value = new_dict[key];
                // Проверяем, что значение не пустое
                if (value !== "" && value !== null && value !== undefined) {
                return `${key}=${value}`;
                }
            })
            .filter(Boolean) // Фильтруем ненулевые значения
            .join('&');

            this.findError = ""
            axios.get(`http://localhost:8080/ips-info/find?${queryString}`)
            .then(res => (this.info = res.data))
            .catch(error => {
                this.findError = error.message + ": " + error.response.data;
            });
        },
        addNewIpInfo(){
            this.addError = ""
            axios.post(`http://localhost:8080/ips-info/add`, [this.to_add])
            .then(res => this.getIpInfo())
            .catch(error => {
                this.addError = error.message + ": " + error.response.data;
            });
        },
        updateIpInfo(){
            this.updateError = ""

            axios.put(`http://localhost:8080/ips-info/update?id=${this.updateId}`)
            .then(res => this.getIpInfo())
            .catch(error => {
                this.updateError = error.message + ": " + error.response.data;
            });
        },
        deleteIpInfo(){
            this.deleteError = ""

            axios.delete(`http://localhost:8080/ips-info/delete?id=${this.deleteId}`)
            .then(res => this.getIpInfo())
            .catch(error => {
                this.deleteError = error.message + ": " + error.response.data;
            });
        }
    }
}
</script>

<template>
    <Header :logo_name="this.logo_name"></Header>
    <section class="find-section">
        <h1>{{ logo_name }}</h1>
        <p>Узнать информацию о{{ filter.ip == "" ? " вашем" : "б"}} IP-адресе {{filter.ip != "" ? ipShow: ""}} </p>
        <input type="text" v-model="filter.ip" placeholder="IP-адрес">
        <input type="text" v-model="filter.city.country" placeholder="Страна">
        <input type="text" v-model="filter.city.countryCode" placeholder="Код страны">
        <input type="text" v-model="filter.city.region" placeholder="Регион">
        <input type="text" v-model="filter.city.regionName" placeholder="Название региона">
        <input type="text" v-model="filter.city.name" placeholder="Название города">
        <input type="text" v-model="filter.city.zip" placeholder="Почтовый индекс">
        <input type="text" v-model="filter.timeZone" placeholder="Часовой пояс">
        <input type="text" v-model="filter.provider.internetServiceProvider" placeholder="Провайдер">
        <input type="text" v-model="filter.provider.organisation" placeholder="Организация">
        <input type="text" v-model="filter.provider.autonomousSystemName" placeholder="Наименование автономной системы">
        <input type="number" v-model="filter.position.latitude" placeholder="Широта">
        <input type="number" v-model="filter.position.longitude" placeholder="Долгота">
        <button @click="getIpInfo()">Найти</button>
        <p class="error">{{ findError }}</p>
    </section>
    <section class="add-section">
        <h1>{{ logo_name }}</h1>
        <p>Добавить информацию об IP-адресе в БД</p>
        <input type="text" v-model="to_add.ip" placeholder="IP-адрес">
        <input type="text" v-model="to_add.city.country" placeholder="Страна">
        <input type="text" v-model="to_add.city.countryCode" placeholder="Код страны">
        <input type="text" v-model="to_add.city.region" placeholder="Регион">
        <input type="text" v-model="to_add.city.regionName" placeholder="Название региона">
        <input type="text" v-model="to_add.city.name" placeholder="Название города">
        <input type="text" v-model="to_add.city.zip" placeholder="Почтовый индекс">
        <input type="text" v-model="to_add.timeZone" placeholder="Часовой пояс">
        <input type="text" v-model="to_add.provider.internetServiceProvider" placeholder="Провайдер">
        <input type="text" v-model="to_add.provider.organisation" placeholder="Организация">
        <input type="text" v-model="to_add.provider.autonomousSystemName" placeholder="Наименование автономной системы">
        <input type="number" v-model="to_add.position.latitude" placeholder="Широта">
        <input type="number" v-model="to_add.position.longitude" placeholder="Долгота">
        <button @click="addNewIpInfo()">Добавить</button>
        <p class="error">{{ addError }}</p>
    </section>
    <section class="update-section">
        <h1>{{ logo_name }}</h1>
        <p>Обновить IP-адрес из БД по ID</p>
        <input type="number" v-model="updateId" placeholder="ID">
        <button @click="updateIpInfo()">Обновить</button>
        <p class="error">{{ updateError }}</p>
    </section>
    <section class="delete-section">
        <h1>{{ logo_name }}</h1>
        <p>Удалить IP-адрес из БД по ID</p>
        <input type="number" v-model="deleteId" placeholder="ID">
        <button @click="deleteIpInfo()">Удалить</button>
        <p class="error">{{ deleteError }}</p>
    </section>
    <MyMap :ipInfo="info"></MyMap>
    <Footer :logo_name="this.logo_name"></Footer>
</template>

<style>
.error {
    color: #d03939;
}

section {
    width: 900px;
    margin-bottom: 50px;
    height: auto;
    border-radius: 50px;
    padding: 50px 20px 20px 20px;
    background-color: #441053;
    text-align: center;
    color: #fff;
}

input {
    margin-top: 30px;
    background: transparent;
    border: 0;
    border-bottom: 2px solid #110813;
    color: #fcfcfc;
    padding: 5px 8px;
    outline: none;
    display: block;
    margin-left: auto;
    margin-right: auto;
}

input:focus {
    border-bottom-color: #6e2d7d;
}

.section p {
    margin-top: 20px;
}

button {
    background: #e3bc4b;
    color: #fff;
    border-radius: 5px;
    border: 2px solid #b99935;
    padding: 10px 15px;
    cursor: pointer;
    transition: transform 500ms ease;
    margin-top: 15px;
    margin-left: auto;
    margin-right: auto;
}

button:hover {
    transform: scale(1.05);
}
</style>
