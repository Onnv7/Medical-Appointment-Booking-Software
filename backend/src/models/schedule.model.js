import mongoose from "mongoose";

const scheduleSchema = mongoose.Schema({
    date: {
        type: Date,
    },
    period: [{
        type: mongoose.Types.ObjectId,
        ref: "Period",
    }],
    doctor: {
        type: mongoose.Types.ObjectId,
        ref: "Doctor",
    }

}, { timestamps: false });

export default mongoose.model("Schedule", scheduleSchema);