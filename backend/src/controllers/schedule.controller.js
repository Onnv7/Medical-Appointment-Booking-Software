import Schedule from "../models/schedule.model.js"
import Doctor from "../models/doctor.model.js"


export const updateSchedule = async (req, res, next) => {
    try {
        const period = req.body.period;
        const newSchedule = await Schedule.findByIdAndUpdate(
            req.params.scheduleId,
            { period: period },
            { new: true }
        );
        res.status(200).json({ success: true, message: "Updated schedule", result: newSchedule });
    } catch (error) {
        next(error);
    }
}

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

export const listSchedule = async (req, res, next) => {
    try {
        const schedule = await Schedule.find({
            doctorId: req.params.doctorId
        })
        res.status(200).json({ success: true, message: "Get schedule list successfully", result: schedule })
    } catch (error) {
        next(error);
    }
}

export const getDetailsOneSchedule = async (req, res, next) => {
    try {
        const schedule = await Schedule.findById(req.params.scheduleId)
            .populate({
                path: 'doctorId',
                select: {
                    'name': 1,
                    'avatarUrl': 1
                }
            });
        res.status(200).json({ successL: true, message: `Get a schedule ${req.params.scheduleId} successfully`, result: schedule })
    } catch (error) {
        next(error);
    }
}