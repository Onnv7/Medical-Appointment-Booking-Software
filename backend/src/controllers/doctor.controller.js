import Booking from "../models/booking.model.js";
import Doctor from "../models/doctor.model.js";
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
        const doctor = await Doctor.findByIdAndUpdate(
            req.params.doctorId,
            { ...req.body },
            { new: true }
        );
        res.status(200).json({ success: true, message: "Update profile successfully", result: doctor });
    } catch (error) {
        next(error);
    }
}

export const getProfileById = async (req, res, next) => {
    try {
        const doctor = await Doctor.findById(req.params.doctorId);

        const { password, ...others } = doctor._doc;
        let rating = await doctor.rating;
        res.status(200).json({ success: true, message: "Find successfully", result: { ...others, rating } });
    } catch (error) {
        next(error)
    }
}

export const getTopDoctor = async (req, res, next) => {
    try {
        const topDoctors = await Doctor.find().sort({ rating: -1 }).limit(req.params.top).populate("specialist")
        const rs = []
        for (const doctor of topDoctors) {
            const dataDoctor = doctor._doc;
            const rating = await doctor.rating;
            const specialist = dataDoctor.specialist?.name || "None";
            const responseData = {
                id: dataDoctor._id,
                name: dataDoctor.name,
                rating: rating,
                specialist: specialist,
                avatarUrl: dataDoctor.avatarUrl
            }
            rs.push({ ...responseData })
        };
        // .select('name avatar');

        res.status(200).json({ success: true, message: "Get top doctor successfully", result: rs })
    } catch (error) {
        next(error);
    }
}

export const getSomeDoctor = async (req, res, next) => {
    try {
        const someDoctors = await Doctor.find().sort({ createdAt: -1 }).limit(req.params.some);
        const rs = []
        for (const doctor of someDoctors) {
            const dataDoctor = doctor._doc;
            const rating = await doctor.rating;
            const responseData = {
                id: dataDoctor._id,
                name: dataDoctor.name,
                rating: rating,
                specialist: dataDoctor.specialist,
                avatarUrl: dataDoctor.avatarUrl,
                price: dataDoctor.price
            }
            rs.push({ ...responseData })
        };
        res.status(200).json({ success: true, message: "Get top doctor successfully", result: rs })
    } catch (error) {
        next(error)
    }
}
export const searchDoctor = async (req, res, next) => {
    try {
        const searchQuery = req.query.content;
        const doctors = await Doctor.find({ name: { $regex: searchQuery, $options: 'i' } })
            .sort({ createdAt: -1 })
            .limit(10)
            .exec();
        res.status(200).json({ success: true, message: "Search successfully", result: doctors });
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
        const successBooking = await Booking.distinct('patient', { doctor: req.params.doctorId, status: 'succeeded' });
        const { password, ...others } = doctor._doc;

        const rating = await doctor.rating;
        const data = {
            name: others.name,
            specialist: others.specialist,
            avatarUrl: others.avatarUrl,
            rating: rating,
            price: others.price,
            clinicName: others.clinicName,
            clinicAddress: others.clinicAddress,
            introduction: others.introduction,
            specialist: others.specialist?.name || "None",
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
