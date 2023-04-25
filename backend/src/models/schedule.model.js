import mongoose from "mongoose";

const scheduleSchema = mongoose.Schema({
    date: {
        type: Date,
        required: true,
    },
    period: [{
        type: mongoose.Types.ObjectId,
        ref: "Period",
        required: true,
    }],
    doctor: {
        type: mongoose.Types.ObjectId,
        ref: "Doctor",
        required: true,
    }

}, { timestamps: false });

export default mongoose.model("Schedule", scheduleSchema);