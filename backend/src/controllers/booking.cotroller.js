import Booking from "../models/booking.model.js";
import Doctor from "../models/doctor.model.js";

export const updateBooking = async (req, res, next) => {
    try {
        const booking = await Booking.findByIdAndUpdate(
            req.params.bookingId,
            { ...req.body }
        );
        res.status(200).json({ success: true, message: "Updated Appointment", result: booking });
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

export const listBookingByPatient = async (req, res, next) => {
    try {
        const bookingList = await Booking.find(
            {
                patientId: req.params.patientId,
                status: { $in: ['waiting', 'aaccepted'] }
            }
        )
            .populate({
                path: 'doctorId',
                select: {
                    'name': 1,
                    'avatarUrl': 1
                }
            })
            .populate({
                path: 'scheduleId',
                select: 'date',
                populate: {
                    path: 'doctorId',
                    select: {
                        'name': 1,
                        'avatarUrl': 1
                    }
                }
            });
        res.status(200).json({ success: true, message: `Get booking list for ${req.params.patientId}`, result: bookingList });
    } catch (error) {
        next(error);
    }
}

export const listBookingByDoctor = async (req, res, next) => {
    console.log("object")
    try {
        const bookingList = await Booking.find(
            {
                doctorId: req.params.doctorId,
                // status: { $in: ['waiting', 'aaccepted'] }
            }
        )
            .populate({
                path: 'patientId',
                select: {
                    'name': 1,
                    'avatarUrl': 1
                }
            })
            .populate({
                path: 'scheduleId',
                select: 'date',
            });
        res.status(200).json({ success: true, message: `Get booking list for ${req.params.patientId}`, result: bookingList });
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

