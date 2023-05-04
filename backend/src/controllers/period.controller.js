import Period from "../models/period.model.js"

export const getAllPeriods = async (req, res, next) => {
    try {
        const periods = await Period.find();
        const morning = []
        const afternoon = []
        const evening = []
        periods.map(p => {
            const timeString = p.start;
            const [hour, minute] = timeString.split(':');
            const numericHour = parseInt(hour);
            if (numericHour >= 0 && numericHour < 12) {
                morning.push(timeString)
            } else if (numericHour >= 12 && numericHour < 18) {
                afternoon.push(timeString)
            } else {
                evening.push(timeString)
            }
        })
        res.status(200).json({ success: true, message: "Get successfully", result: { morning, afternoon, evening } })
    } catch (error) {
        next(error)
    }
}