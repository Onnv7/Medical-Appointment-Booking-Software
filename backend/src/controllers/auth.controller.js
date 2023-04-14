import bcrypt from "bcryptjs";
import { createError } from "../utils/error.js";
import jwt from "jsonwebtoken";
import Patient from "../models/patient.model.js";
import Doctor from "../models/doctor.model.js";
import { sendEmail } from './../utils/sendEmail.js';
import Booking from "../models/booking.model.js";
import Schedule from "../models/schedule.model.js"


export const sendCodeVerify = async (req, res, next) => {
    try {
        const code = Math.floor(Math.random() * 9000) + 1000;
        const isSent = await sendEmail(req.body.email, "Your code: ", "" + code);
        if (isSent)
            res.status(200).json({ success: true, message: "Sent code successfully", result: code });
        else
            res.status(200).json({ success: false, message: "Cant send code" });

    } catch (error) {
        next(error);
    }
};

export const createSchedule = async (req, res, next) => {
    try {
        const doctorId = req.body.doctorId;

        const doctor = await Doctor.findById(doctorId);
        if (!doctor) {
            return res.status(404).json({ success: false, message: `Dont find doctor ${doctorId}` });
        }

        const date = new Date(req.body.date);
        const today = new Date();
        const tomorrow = new Date(today.getFullYear(), today.getMonth(), today.getDate() + 1);

        const nextWeek = new Date(today.getFullYear(), today.getMonth(), today.getDate() + 8);

        if (date <= tomorrow || date >= nextWeek) {
            return res.status(200).json({ success: false, message: "Date is invalid" })
        }

        const schedule = new Schedule({
            ...req.body
        });
        await schedule.save();
        res.status(200).json({ success: true, message: "Created Schedule", result: schedule })
    } catch (error) {
        next(error);
    }
}

export const createBooking = async (req, res, next) => {
    try {
        const booking = new Booking({ ...req.body });
        await booking.save();
        res.status(200).json({ success: true, message: "Creared booking", result: booking });
    } catch (error) {
        next(error);
    }
}


// register a new patient
export const registerNewPatient = async (req, res, next) => {
    try {
        const salt = bcrypt.genSaltSync(10);
        const hash = bcrypt.hashSync(req.body.password, salt);
        const newPatient = new Patient({
            birthDate: "",
            avatarUrl: "https://res.cloudinary.com/dtvnsczg8/image/upload/v1681464004/Clinic/avatar/patient/patient_avatar_default.png",
            phone: "",
            address: "",
            ...req.body,
            password: hash,
        });
        await newPatient.save();
        res.status(200).json({ success: true, message: "Success" });
    } catch (err) {
        next(err);
    }
};

export const loginPatient = async (req, res, next) => {

    try {
        const patient = await Patient.findOne({ email: req.body.email });

        if (!patient) return next(createError(404, "Patient not found!"));

        const isPasswordCorrect = await bcrypt.compare(
            req.body.password,
            patient.password
        );

        if (!isPasswordCorrect)
            throw createError(400, "Wrong password or username!");

        const token = jwt.sign({ id: patient._id, email: patient.email }, "an");
        const { avatar, password, ...otherDetails } = patient._doc;

        res.cookie("access_token", token)
            .status(200)
            .json({ success: true, message: "Success", result: { ...otherDetails } });
    } catch (err) {
        next(err);
    }
};

export const registerNewDoctor = async (req, res, next) => {
    // TODO: sửa lỗi chỗ ngày sinh trong mongoose khác với date tong js => sử dụng 'yyyy-mm-dd để fix
    try {
        const avatarUrl = "https://res.cloudinary.com/dtvnsczg8/image/upload/v1681134360/Clinic/avatar/doctor/doctor_avatar_default.png"
        const salt = bcrypt.genSaltSync(10);
        const hash = bcrypt.hashSync(req.body.password, salt);
        const newDoctor = new Doctor({
            ...req.body,
            password: hash,
            avatarUrl: avatarUrl
        });
        await newDoctor.save();
        res.status(200).json({ success: true, message: "Success" });
    } catch (err) {
        next(err);
    }
};

export const loginDoctor = async (req, res, next) => {

    try {
        const doctor = await Doctor.findOne({ email: req.body.email });

        if (!doctor) return next(createError(404, "Patient not found!"));

        const isPasswordCorrect = await bcrypt.compare(
            req.body.password,
            doctor.password
        );

        if (!isPasswordCorrect)
            throw createError(400, "Wrong password or username!");

        const token = jwt.sign({ id: doctor._id, email: doctor.email }, "an");
        const { avatar, password, ...otherDetails } = doctor._doc;

        res.cookie("access_token", token)
            .status(200)
            .json({ success: true, message: "Success", result: { ...otherDetails } });
    } catch (err) {
        next(err);
    }
};



export const changePassword = async (req, res, next) => {
    try {
        const salt = bcrypt.genSaltSync(10);
        const hash = bcrypt.hashSync(req.body.password, salt);

        await Patient.updateOne(
            { _id: req.params.userId },
            { password: hash }
        )
        res.status(200).json({ success: true, message: "Change password successfully" });
    } catch (error) {
        next(error);
    }
};
