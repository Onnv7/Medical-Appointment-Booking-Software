import Booking from "../models/booking.model.js";
import Doctor from "../models/doctor.model.js";
import { formatMongooseTime } from "../utils/formatTime.js"
import moment from "moment";
import Period from "../models/period.model.js";
import Schedule from "../models/schedule.model.js";
import cloudinary from "../utils/cloudinary.js";


// export const createDoctor = async (req, res, next) => {
//     try {
//         const doctor = new Doctor({
//             ...req.body, avatarUrl
//         });
//         doctor.save();
//         res.status(200).json({ success: true, message: "Doctor created successfully" });
//     } catch (error) {
//         next(error);
//     }
// }



export const updateProfile = async (req, res, next) => {
    try {
        const doctorId = req.params.doctorId;
        let type;
        let image, avatarUrl = "";
        let body = req.body;
        if (req.file) {
            type = req.file.mimetype
            image = `data:${type};base64,` + req.file.buffer.toString('base64');
            const uploadedResponse = await cloudinary.uploader.upload(image, {
                public_id: doctorId,
                upload_preset: 'doctor avatar'
            })
            avatarUrl = uploadedResponse.url
            body = {
                ...body,
                avatarUrl
            }
        }

        const doctor = await Doctor.findByIdAndUpdate(
            doctorId,
            body,
            { new: true },
        );

        const { password, ...others } = doctor._doc;
        res.status(200).json({ success: true, message: "Update profile successfully", result: others })
    } catch (error) {
        next(error);
    }
}

export const getProfileById = async (req, res, next) => {
    try {
        const doctor = await Doctor.findById(req.params.doctorId).populate('specialist');
        const birthDate = formatMongooseTime("YYYY-MM-DD", doctor.birthDate)
        const { password, ...others } = doctor._doc;
        let rating = await doctor.rating;
        res.status(200).json({ success: true, message: "Find successfully", result: { ...others, rating, birthDate } });
    } catch (error) {
        next(error)
    }
}

export const getTopDoctor = async (req, res, next) => {
    try {
        const topDoctors = await Doctor.find().sort({ rating: -1 }).limit(req.params.top).populate("specialist")
        let rs = []
        for (const doctor of topDoctors) {
            const dataDoctor = doctor._doc;
            const rating = await doctor.rating;
            const specialist = dataDoctor.specialist;
            const responseData = {
                _id: dataDoctor._id,
                name: dataDoctor.name,
                rating: !rating ? 0 : rating,
                specialist: specialist,
                avatarUrl: dataDoctor.avatarUrl
            }
            rs.push({ ...responseData })
        };
        // .select('name avatar');
        rs = rs.sort((a, b) => b.rating - a.rating)
        res.status(200).json({ success: true, message: "Get top doctor successfully", result: rs })
    } catch (error) {
        next(error);
    }
}

export const getSomeDoctor = async (req, res, next) => {
    try {
        const someDoctors = await Doctor.find().sort({ createdAt: -1 }).limit(req.params.some).populate("specialist");
        const rs = []
        for (const doctor of someDoctors) {
            const dataDoctor = doctor._doc;
            const rating = await doctor.rating;
            const responseData = {
                _id: dataDoctor._id,
                name: dataDoctor.name,
                rating: !rating ? 0 : rating,
                specialist: dataDoctor.specialist,
                avatarUrl: dataDoctor.avatarUrl,
                price: dataDoctor.price
            }
            rs.push({ ...responseData })
        };
        res.status(200).json({ success: true, message: "Get some doctors successfully", result: rs })
    } catch (error) {
        next(error)
    }
}
export const searchDoctor = async (req, res, next) => {
    try {
        const searchQuery = req.query.content;
        const doctors = await Doctor.find({ name: { $regex: searchQuery, $options: 'i' } })
            .sort({ createdAt: -1 })
            .populate('specialist')
            .select({ name: 1, avatarUrl: 1, price: 1, rating: 1 })
            .exec();
        const doctorsWithRating = [];
        for (const doctor of doctors) {
            const patients = await Booking.distinct('patient', { doctor: doctor._id });
            const dt = {
                _id: doctor._id,
                name: doctor.name,
                avatarUrl: doctor.avatarUrl,
                price: doctor.price,
                specialist: doctor.specialist ? doctor.specialist : "Unknown",
                patientQuantity: patients.length
            }
            doctorsWithRating.push(dt)
        };
        res.status(200).json({ success: true, message: "Search successfully", result: doctorsWithRating });
    } catch (error) {
        next(error);
    }
}

export const getInfoDoctorById = async (req, res, next) => {
    try {
        const doctor = await Doctor.findById(req.params.doctorId).populate({
            path: 'specialist',
            select: { name: 1 }
        });
        const patients = await Booking.distinct('patient', { doctor: req.params.doctorId });
        const successBooking = await Booking.find({ doctor: req.params.doctorId, status: 'succeeded' });
        const { password, ...others } = doctor._doc;

        const rating = await doctor.rating;
        const data = {
            _id: others._id,
            name: others.name,
            specialist: others.specialist,
            avatarUrl: others.avatarUrl,
            rating: rating,
            price: others.price,
            clinicName: others.clinicName,
            clinicAddress: others.clinicAddress,
            phone: others.phone,
            introduction: others.introduction,
            specialist: others.specialist || "Unknown",
            patientQuantity: patients.length,
            successBookingQuantity: successBooking.length
        }

        res.status(200).json({ success: true, message: "Get information doctor successfully", result: data });
    } catch (error) {
        next(error);
    }
}

export const getReviewsByDoctorId = async (req, res, next) => {
    try {
        const bookingList = await Booking.find({
            doctor: req.params.doctorId
        }).populate("patient")

        res.status(200).json({ success: true, message: "Get review doctor successfully", result: bookingList })
    } catch (error) {
        next(error);
    }
}

// TODO: dang lam do
export const getSpecificationsDoctor = async (req, res, next) => {
    try {
        const booking = await Booking.countDocuments(
            {
                doctor: req.params.doctorId,
                status: over
            }
        )
        const data = {
            review: booking,
            patient: 12
        }
        res.status(200).json({ success: true, message: "Get specificationDoctor successfully", result: data })
    } catch (error) {
        next(error)
    }
}

export const getDoctorsBySpecialist = async (req, res, next) => {
    try {
        const doctors = await Doctor.find({ specialist: req.params.specialistId })
            .sort({ createdAt: -1 })
            .populate('specialist')
            .select({ name: 1, avatarUrl: 1, price: 1, rating: 1 })
            .exec();
        const doctorsWithRating = [];
        for (const doctor of doctors) {
            const patients = await Booking.distinct('patient', { doctor: doctor._id });
            const dt = {
                _id: doctor._id,
                name: doctor.name,
                avatarUrl: doctor.avatarUrl,
                price: doctor.price,
                specialist: doctor.specialist,
                patientQuantity: patients.length
            }
            doctorsWithRating.push(dt)
        };
        res.status(200).json({ success: true, message: "Get successfully", result: doctorsWithRating });
    } catch (error) {
        next(error)
    }
}

export const getAllDoctors = async (req, res, next) => {
    try {
        const doctors = await Doctor.find()
            .sort({ createdAt: -1 })
            .populate('specialist')
            .select({ name: 1, avatarUrl: 1, price: 1, rating: 1 })
            .exec();
        const doctorsWithRating = [];
        for (const doctor of doctors) {
            const patients = await Booking.distinct('patient', { doctor: doctor._id })
            const dt = {
                _id: doctor._id,
                name: doctor.name,
                avatarUrl: doctor.avatarUrl,
                price: doctor.price,
                specialist: doctor.specialist,
                patientQuantity: patients.length
            }
            doctorsWithRating.push(dt)
        };
        res.status(200).json({ success: true, message: "Find successfully", result: doctorsWithRating })
    } catch (error) {
        next(error)
    }
}
export const isExistedDoctor = async (req, res, next) => {
    try {
        const patient = await Doctor.findOne({ email: req.query.email });
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
