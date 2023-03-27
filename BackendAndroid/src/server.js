var express = require('express')
import bodyParser from "body-parser";
import viewEngine from "./config/viewEngine"
import initWebRoutesPatient from "./route/webPatient"
import initWebRoutesDoctor from './route/webDoctor'
import connectDB from './config/connectDB'
import cors from 'cors'
import doctorRoute from './route/webDoctor.js'
import patientRoute from './route/webPatient.js'
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

app.use(bodyParser.json({ limit: '50mb' }))
app.use(bodyParser.urlencoded({ limit: '50mb', extended: true }))
// app.use(bodyParser.json())
// app.use(bodyParser.urlencoded({extended: true}))

app.use('/api/doctor', doctorRoute)
app.use('/api/patient', patientRoute)

app.use((err, req, res, next) => {
    const errorStatus = err.status || 500;
    const errorMessage = err.message || "Something went wrong!";
    return res.status(500).json({
        success: false,
        status: errorStatus,
        message: errorMessage,
        stack: err.stack,
    });
})

viewEngine(app)


connectDB()

let port = process.env.PORT || 8080
app.listen(port, () => {
    // callback
    console.log("Running................ port: " + port)
})
