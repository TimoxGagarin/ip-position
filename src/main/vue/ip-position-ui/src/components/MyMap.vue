<template>
    <div id="mapContainer"></div> 
</template>

<style scoped>
#mapContainer {
    height: 800px;
    border-radius: 50px;
    margin: 0 0 10px 10px;
}

.card-h1{
    font-size: 14px;
}

.card{
    text-align: center;
}
</style>

<script>
    import L from "leaflet";
import "leaflet/dist/leaflet.css";

    export default{
        props: {
            ipInfo: {
                type: Object,
                required: false
            }
        },
        data() {
            return {
                map: null,
                markers: []
            }
        },
        mounted() {
            this.map = L.map("mapContainer").setView([0, 0], 2);
            L.tileLayer("http://{s}.tile.osm.org/{z}/{x}/{y}.png", {
            attribution:
                '&copy; <a href="http://osm.org/copyright">OpenStreetMap</a> contributors',
            }).addTo(this.map);
            
            var customPane = this.map.createPane("customPane");
            var canvasRenderer = L.canvas({ pane: "customPane" });
            customPane.style.zIndex = 399; // put just behind the standard overlay pane which is at 400
            this.addMarkers(this.ipInfo);
        },
        onBeforeUnmount() {
            if (this.map) {
            this.map.remove();
            }
        },
        watch: {
            ipInfo: {
                handler: function(newIpInfo){
                    this.addMarkers(newIpInfo)
                },
                deep: true
            }
        },
        computed: {
            ipShow() {
                return 
            }
        },
        methods: {
            addMarkers(ipInfos){
                while(this.markers.length > 0){
                    this.map.removeLayer(this.markers.pop());
                }
                console.log(ipInfos);
                this.map.setView([0, 0], 2);
                for(let i in ipInfos){
                    console.log(ipInfos[i]);
                    this.markers.push(L.marker([ipInfos[i].position.latitude, ipInfos[i].position.longitude]).addTo(this.map)
                    .bindPopup(`
                            <style>
                            .card-h1{
                                font-size: 14px;
                            }
                            .card{
                                text-align: left;
                            }
                            </style>
                            <h1 class="card-h1">Информация об IP-адресе ${ipInfos[i].ip}</h1>
                            <table class="card">
                                <tr>
                                    <td>ID</td>
                                    <td>${ ipInfos[i].id }</td>
                                </tr>
                                <tr>
                                    <td>Часовой пояс</td>
                                    <td>${ ipInfos[i].timeZone }</td>
                                </tr>
                            </table>
                            <table class="card">
                            <th>Информация о городе</th>
                            <tr>
                                <td>Название</td>
                                <td>${ ipInfos[i].city.name }</td>
                            </tr>
                            <tr>
                                <td>Страна</td>
                                <td>${ ipInfos[i].city.country }</td>
                            </tr>
                            <tr>
                                <td>Код страны</td>
                                <td>${ ipInfos[i].city.countryCode }</td>
                            </tr>
                            <tr>
                                <td>Регион</td>
                                <td>${ ipInfos[i].city.region }</td>
                            </tr>
                            <tr>
                                <td>Название региона</td>
                                <td>${ ipInfos[i].city.regionName }</td>
                            </tr>
                            <tr>
                                <td>Почтовый индекс</td>
                                <td>${ ipInfos[i].city.zip }</td>
                            </tr>
                        </table>
                        <table class="card">
                            <th>Информация о провайдере</th>
                            <tr>
                                <td>Провайдер</td>
                                <td>${ ipInfos[i].provider.internetServiceProvider }</td>
                            </tr>
                            <tr>
                                <td>Организация</td>
                                <td>${ ipInfos[i].provider.organisation }$</td>
                            </tr>
                            <tr>
                                <td>Наименование автономной системы</td>
                                <td>${ ipInfos[i].provider.autonomousSystemName }</td>
                            </tr>
                        </table> 
                        `)
                    .openPopup());
                }
            }
        }
    }
    
</script>