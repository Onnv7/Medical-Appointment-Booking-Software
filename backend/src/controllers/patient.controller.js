import Patient from "../models/patient.model.js";
import Booking from "../models/booking.model.js";
import cloudinary from "../utils/cloudinary.js";

// export const bookingAppointment = async (req, res, next) => {
//     try {
//         const advice = req.body.advice;
//         const patientId = req.body.patientId;
//         const timeId = req.body.timeId;
//         await Booking.findByIdAndUpdate(
//             req.params.bookingId,
//             {
//                 advice: advice,
//                 patient: patientId,
//                 time: timeId
//             },
//             { new: true },
//         );
//         res.status(200).json({ success: true, message: "Booking updated successfully" })
//     } catch (error) {
//         next(error);
//     }
// }

export const updateProfile = async (req, res, next) => {
    try {
        const patientId = req.params.patientId;
        let type;
        let image, avatarUrl = "";
        let body = req.body;
        if (req.file) {
            type = req.file.mimetype
            image = `data:${type};base64,` + req.file.buffer.toString('base64');
            const uploadedResponse = await cloudinary.uploader.upload(image, {
                public_id: patientId,
                upload_preset: 'patient avatar'
            })
            avatarUrl = uploadedResponse.url
            body = {
                ...body,
                avatarUrl
            }
        }

        const patient = await Patient.findByIdAndUpdate(
            patientId,
            body,
            { new: true },
        );

        const { password, ...others } = patient._doc;
        res.status(200).json({ success: true, message: "Update profile successfully", result: others })
    } catch (error) {
        // console.log(error)
        next(error);
    }
}

export const updateAvatar = async (req, res, next) => {
    try {
        console.log(req.file)
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

export const getProfileById = async (req, res, next) => {
    try {
        const patient = await Patient.findById(req.params.patientId);
        if (!patient) {
            return res.status(200).json({ success: false, message: "Find failed" });
        }
        const { password, ...others } = patient._doc;
        res.status(200).json({ success: true, message: "Find successfully", result: others })
    } catch (error) {
        next(error);
    }
}
export const isExistedPatient = async (req, res, next) => {
    try {
        const patient = await Patient.findOne({ email: req.query.email });
        if (patient) {
            return res.status(200).json({ success: true, message: "Patient already exists", result: true })
        }
        else {
            return res.status(200).json({ success: true, message: "Patient is not existed", result: false })
        }
    } catch (error) {
        next(error)
    }
}
