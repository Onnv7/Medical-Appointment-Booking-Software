import mongoose from "mongoose";

const periodSchema = mongoose.Schema({
    start: {
        type: String,
    }
}, { timestamps: false });

export default mongoose.model("Period", periodSchema);