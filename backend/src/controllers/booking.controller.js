import Booking from "../models/booking.model.js";
import Doctor from "../models/doctor.model.js";
import { formatMongooseTime, formatFullDate } from "../utils/formatTime.js";
import { format } from "date-fns"
import moment from "moment";
export const updateBooking = async (req, res, next) => {
    try {
        const booking = await Booking.findByIdAndUpdate(
            req.params.bookingId,
            { ...req.body },
            { new: true }
        );
        if (!booking)
            return res.status(200).json({ success: false, message: "Not found booking" });
        res.status(200).json({ success: true, message: "Updated Appointment", result: booking });
    } catch (error) {
        next(error);
    }
}

export const createBooking = async (req, res, next) => {
    try {
        const oldBooking = await Booking.findOne(
            {
                patient: req.body.patient,
                doctor: req.body.doctor,
                time: req.body.time
            }
        );

        if (oldBooking) {
            return res.status(200).json({ success: false, message: "Existed", result: oldBooking });
        }
        const booking = new Booking({ ...req.body, star: 0, review: null });
        await booking.save();
        res.status(200).json({ success: true, message: "Created booking", result: booking });
    } catch (error) {
        next(error);
    }
}

export const listBookingByPatient = async (req, res, next) => {
    try {
        const bookingList = await Booking.find(
            {
                patient: req.params.patientId,
                status: { $in: ['waiting', 'accepted'] }
            }
        )
            .populate({
                path: 'doctor',
                select: {
                    'name': 1,
                    'avatarUrl': 1
                }
            }).sort({ time: -1 });
        // .populate({
        //     // path: 'schedule',
        //     // select: 'date',
        //     // populate: {
        //     //     path: 'doctor',
        //     //     select: {
        //     //         'name': 1,
        //     //         'avatarUrl': 1
        //     //     }
        //     // }
        // });
        if (bookingList) {
            res.status(200).json({ success: true, message: `Get booking list for ${req.params.patientId}`, result: bookingList });
        }
        else {
            res.status(200).json({ success: false, message: "Empty" });
        }
    } catch (error) {
        next(error);
    }
}

export const listBookingByDoctor = async (req, res, next) => {
    try {
        const date = moment(req.query.date, 'DD/MM/YYYY');
        const outputDate = date.format('DD MMM YYYY');
        const bookingList = await Booking.find(
            {
                time: {
                    $regex: new RegExp(outputDate, 'i'), // tìm chuỗi con chứa ngày
                },
                doctor: req.params.doctorId,
                status: 'accepted'
            }
        )
            .populate({
                path: 'patient',
                select: {
                    'name': 1,
                    'avatarUrl': 1,
                    'phone': 1,
                }
            }).select({ patient: 1, time: 1, createdAt: 1 });
        res.status(200).json({ success: true, message: `Get booking list for ${req.params.doctorId}`, result: bookingList });
    } catch (error) {
        next(error);
    }
}


export const getAllBooking = async (req, res, next) => {
    try {
        const list = await Booking.find();
        res.status(200).json(list);
    } catch (error) {

    }
}

export const getHistoryList = async (req, res, next) => {
    try {
        const bookings = await Booking.find({
            patient: req.params.patientId,
            status: { $in: ["denied", "succeeded"] }
        }).populate({
            path: "doctor",
            select: { name: 1, avatarUrl: 1 }
        }).select("doctor time status")
            .sort({ time: -1 });
        if (bookings.length > 0)
            return res.status(200).json({ success: true, message: "Get history list successfully", result: bookings })
        else
            return res.status(200).json({ success: false, message: "History list is empty" })
    } catch (error) {
        next(error)
    }
}

export const createDataBookingTool = async (req, res, next) => {
    try {
        const bookings = new Booking({ ...req.body });
        bookings.save();
        res.status(200).json({ success: true, message: "OK" })
    } catch (error) {
        next(error)
    }
}

export const getHistoryAppointmentDetails = async (req, res, next) => {
    try {
        const appointment = await Booking.findById(req.params.appointmentId)
            .populate({
                path: "doctor",
                select: { avatarUrl: 1, name: 1 }
            }).populate({
                path: "review",
                select: { star: 1, description: 1 }
            })
            .select({ patient: 0, updatedAt: 0 });
        if (!appointment) return res.status(200).json({ success: false, message: "Not found" });
        const createdAt = formatMongooseTime("DD MMMM YYYY HH:mm", appointment.createdAt)
        const star = appointment.star ? appointment.star : 0
        if (appointment) {
            return res.status(200).json({ success: true, message: "Get appointment details successfully", result: { ...appointment._doc, createdAt, star } })
        }
        return res.status(200).json({ success: false, message: "Not found" })
    } catch (error) {
        next(error);
    }
}

export const getBookingDetailsById = async (req, res, next) => {
    try {
        const appointment = await Booking.findById(req.params.bookingId)
            .populate({
                path: "patient",
                select: { avatarUrl: 1, name: 1, phone: 1 }
            }).populate({
                path: "review",
                select: { star: 1, description: 1 }
            })
            .select({ doctor: 0, updatedAt: 0 });
        if (!appointment) return res.status(200).json({ success: false, message: "Not found" });
        const createdAt = formatMongooseTime("DD MMMM YYYY HH:mm", appointment.createdAt)

        if (appointment) {
            return res.status(200).json({ success: true, message: "Get appointment details successfully", result: { ...appointment._doc, createdAt } })
        }
        return res.status(200).json({ success: false, message: "Not found" })
    } catch (error) {
        next(error);
    }
}

export const listNewBooking = async (req, res, next) => {
    try {
        const date = new Date(req.query.date);
        const day = new Date("2023-05-23")

        const bookingList = await Booking.find(
            {
                doctor: req.params.doctorId,
                status: 'waiting'
            }
        )
            .populate({
                path: 'patient',
                select: {
                    'name': 1,
                    'avatarUrl': 1,
                    'phone': 1,
                }
            })
            .select({ patient: 1, time: 1, createdAt: 1 });
        const result = [];
        bookingList.forEach(booking => {
            const time = new Date(formatFullDate('YYYY-MM-DD', booking.time))
            if (time.getTime() >= date.getTime()) {
                result.push(booking)
            }
        })

        res.status(200).json({ success: true, message: `Get booking list for ${req.params.doctorId}`, result: result });
    } catch (error) {
        next(error);
    }
}
