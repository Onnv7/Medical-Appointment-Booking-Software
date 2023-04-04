import Patient from "../models/patient.model.js";
import Booking from "../models/booking.model.js";
import cloudinary from "../utils/cloudinary.js";
export const bookingAppointment = async (req, res, next) => {
    try {
        const advice = req.body.advice;
        const patientId = req.body.patientId;
        const timeId = req.body.timeId;
        await Booking.findByIdAndUpdate(
            req.params.bookingId,
            {
                advice: advice,
                patientId: patientId,
                timeId: timeId
            },
            { new: true },
        );
        res.status(200).json({ success: true, message: "Booking updated successfully" })
    } catch (error) {
        next(error);
    }
}

export const updateProfile = async (req, res, next) => {
    try {
        const patient = await Patient.findByIdAndUpdate(
            req.params.patientId,
            { ...req.body },
            { new: true },
        );
        res.status(200).json({ success: true, message: "Update profile successfully", result: patient })
    } catch (error) {
        next(error);
    }
}

export const updateAvatar = async (req, res, next) => {
    try {
        const patientId = req.params.patientId;
        const type = req.file.mimetype
        const image = `data:${type};base64,` + req.file.buffer.toString('base64');

        const uploadedResponse = await cloudinary.uploader.upload(image, {
            public_id: patientId,
            upload_preset: 'patient avatar'
        })
        const avatarUrl = uploadedResponse.url
        const result = await Patient.findByIdAndUpdate(
            patientId,
            { avatarUrl: avatarUrl },
            { new: true },
        )
        res.status(200).json({ success: true, message: "Update avatar successfully", result: result })
    } catch (error) {
        // console.log(error)
        next(error);
    }
}