var express = require('express')
import bodyParser from "body-parser";
import viewEngine from "./config/viewEngine"
import initWebRoutesPatient from "./route/patient/webPatient"
import initWebRoutesDoctor from './route/doctor/webDoctor' 
import connectDB from './config/connectDB'
import cors from 'cors'
require('dotenv').config()
let app = express()

//Sửa lỗi CORS (stackoverflow https://stackoverflow.com/questions/58372337/how-to-fix-cors-error-request-doesnt-pass-access-control-check)
app.use(function (req, res, next) {

    res.setHeader('Access-Control-Allow-Origin', process.env.URL_REACT);
    res.setHeader('Access-Control-Allow-Methods', 'GET, POST, OPTIONS, PUT, PATCH, DELETE');
    res.setHeader('Access-Control-Allow-Headers', 'X-Requested-With,content-type');
    res.setHeader('Access-Control-Allow-Credentials', true);
    next();
});

app.use(bodyParser.json({limit: '50mb'}))
app.use(bodyParser.urlencoded({limit:'50mb', extended: true}))
// app.use(bodyParser.json())
// app.use(bodyParser.urlencoded({extended: true}))

viewEngine(app)
initWebRoutesPatient(app)
initWebRoutesDoctor(app)

connectDB()

let port = process.env.PORT || 8080
app.listen(port , () => {
    // callback
    console.log("Running................ port: "+port)
})
