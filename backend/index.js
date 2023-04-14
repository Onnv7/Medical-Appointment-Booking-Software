import express from "express";
import cors from "cors";
import dotenv from "dotenv";
import mongoose from "mongoose";
import crypto from "crypto";
import authRoute from "./src/routers/auth.route.js";
import patientRoute from "./src/routers/patient.route.js";
import doctorRoute from "./src/routers/doctor.route.js";
import specialistRoute from "./src/routers/specialist.route.js";
import bookingRoute from "./src/routers/booking.route.js";
import scheduleRoute from "./src/routers/schedule.route.js";


import bodyParser from 'body-parser';
const app = express();
dotenv.config();

const connect = async () => {
    try {
        // "mongodb+srv://lama:<password>@cluster0.1qwmnmy.mongodb.net/test"
        // "mongodb://127.0.0.1:27017/Zola"
        await mongoose.connect("mongodb://127.0.0.1:27017/Clinic");
        console.log("Connected to mongoDB.");
    } catch (error) {
        throw error;
    }
};
mongoose.connection.on("disconnected", () => {
    console.log("mongoDB disconnected!");
});

mongoose.connection.on("connected", () => {
    console.log("mongoDB connected!");
});

app.use(cors({ credentials: true, origin: true }));
app.use(express.json({ limit: '50mb' }));
app.use(express.urlencoded({ limit: '50mb', extended: true }));
app.use(bodyParser.json()); // for parsing application/json
app.use(bodyParser.urlencoded({ extended: true }));


app.use("/api/auth", authRoute);
app.use("/api/patient", patientRoute);
app.use("/api/doctor", doctorRoute);
app.use("/api/specialist", specialistRoute);
app.use("/api/booking", bookingRoute);
app.use("/api/schedule", scheduleRoute);

app.use((err, req, res, next) => {
    const errorStatus = err.status || 500;
    const errorMessage = err.message || "Something went wrong!";
    return res.status(500).json({
        success: false,
        status: errorStatus,
        message: errorMessage,
        stack: err.stack,
    });
});

// app.get('/list',(req,res)=>{
//     res.send("vao")
// })
const PORT = process.env.PORT || 8080
app.listen(PORT, () => {
    connect();
    console.log(`Connected to backend: http://localhost:${PORT}`);
});
