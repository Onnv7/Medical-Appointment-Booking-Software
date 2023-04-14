import Schedule from "../models/schedule.model.js"
import Doctor from "../models/doctor.model.js"

import Period from "../models/period.model.js";

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
            doctor: req.params.doctorId
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
                path: 'doctor',
                select: {
                    'name': 1,
                    'avatarUrl': 1
                }
            });
        res.status(200).json({ success: true, message: `Get a schedule ${req.params.scheduleId} successfully`, result: schedule })
    } catch (error) {
        next(error);
    }
}

export const getScheduleByDate = async (req, res, next) => {
    try {
        console.log(req.body.date, new Date(req.body.date))
        const schedule = await Schedule.find({
            date: { $eq: new Date(req.body.date) },
            doctor: req.params.doctorId
        })
        console.log(schedule)
        res.status(200).json({ success: true, message: "Get successfully", result: schedule })
    } catch (error) {
        next(error);
    }
}
export const createScheduleTools = async (req, res, next) => {
    try {
        const doctor = await Doctor.find({
            __id: req.params.doctorId
        });
        const period = await Period.find({});
        const time = []
        for (let i = 0; i < period.length; i++) {
            time.push(period[i]._id);
        }
        const schedule = new Schedule(
            {
                date: req.body.date,
                period: time,
                doctor: req.params.doctorId
            }
        );
        await schedule.save();
        res.status(200).json({ success: true, message: "create successfully", result: schedule })
    } catch (error) {
        next(error)
    }
}
export const getAvailableSchedule = async (req, res, next) => {
    try {
        const start = new Date(req.body.start);
        const end = new Date(req.body.end);
        const doctorId = req.params.doctorId;
        const scheduleData = await Schedule.find({
            date: { $gte: start, $lte: end },
            doctor: doctorId
        }).populate('period').lean();
        const result = [];
        const currentDate = new Date(start);
        for (let i = 0; i < 7; i++) {
            const dateString = currentDate.toISOString().split('T')[0];
            const periodCount = scheduleData.filter(schedule => {
                return schedule.date.toISOString().split('T')[0] === dateString;
            }).reduce((total, schedule) => {
                return total + schedule.period.length;
            }, 0);
            result.push({
                date: dateString,
                available: periodCount
            });
            currentDate.setDate(currentDate.getDate() + 1);
        }
        res.status(200).json({ success: true, message: "Get schedule available successfully", result: result });
    } catch (error) {
        next(error)
    }
}

export const getTimeSlotInDate = async (req, res, next) => {
    try {
        const date = new Date(req.query.date);
        const slot = await Schedule.findOne({
            date: { $eq: date },
            doctor: req.params.doctorId
        }).populate({
            path: 'period',
            select: { _id: 0 }
        })
        if (slot === null || slot.period.length === 0) {
            return res.status(200).json({ success: false, message: "There is no schedule available" })
        }

        const period = slot.period;
        const morning = []
        const afternoon = []
        const evening = []
        period.map(p => {
            const timeString = p.start;
            const [hour, minute] = timeString.split(':');
            const numericHour = parseInt(hour);
            if (numericHour >= 0 && numericHour < 12) {
                morning.push(timeString)
            } else if (numericHour >= 12 && numericHour < 18) {
                afternoon.push(timeString)
            } else {
                afternoon.push(timeString)
            }
        })
        res.status(200).json({
            success: true, message: "Get successfully", result: {
                morning: morning,
                afternoon: afternoon,
                evening: evening
            }
        });
    } catch (error) {
        next(error)
    }
}